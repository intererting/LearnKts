package dsl

import asset.AddAssetTask
import com.android.build.api.artifact.MultipleArtifact
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property.*
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.dsl.ApplicationExtension

/**
 * @author    yiliyang
 * @date      2022/4/12 下午4:04
 * @version   1.0
 * @since     1.0
 */
abstract class ToyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val android = project.extensions.getByType(ApplicationExtension::class.java)

        android.buildTypes.forEach {
            it.extensions.add("toy", ToyExtension::class.java)
        }

        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)

        //在此回调中，您可以访问和修改通过解析 build 文件中 android 代码块的信息而创建的 DSL 对象。这些 DSL 对象将用来在 build 的后续阶段中初始化和配置变体。例如，
        // 您可以通过编程方式创建新配置或替换属性，但请注意，所有值都必须在配置时进行解析，因此它们不能依赖于任何外部输入。
        // 此回调执行完毕后，DSL 对象将不再有用，您不应再保留对它们的引用或修改它们的值。
        androidComponents.finalizeDsl { extension ->
            extension.buildTypes.create("extra").let {
                it.isJniDebuggable = true
            }
        }

        //在 build 过程的这一阶段，您可以访问 VariantBuilder 对象，这些对象决定了将要创建的变体及其属性。
        // 例如，您可以通过编程方式停用某些变体及其测试，或者仅针对所选变体更改某个属性的值（例如 minSdk）。
        // 与 finalizeDsl() 类似，您提供的所有值都必须在配置时进行解析，且不得依赖于外部输入。beforeVariants()
        // 回调执行完毕后，您不得修改 VariantBuilder 对象。
        androidComponents.beforeVariants(androidComponents.selector().withBuildType("debug")) {
            it.minSdk = 21
        }

        //在调用 onVariants() 时，AGP 将会创建的所有工件均已确定，因此您无法再停用它们。不过，您可以通过为 Variant 对象中的 Property 属性设置值来修改任务所使用的某些值
        // 。由于 Property 值只会在执行 AGP 的任务时进行解析，因此您可以放心地将这些值从您自己的自定义任务连接到提供程序；
        // 您的自定义任务将执行任何所需的计算，包括从文件或网络等外部输入中读取内容。
        androidComponents.onVariants { variant ->
            val buildType = android.buildTypes.getByName(variant.buildType)
            val toyExtension = buildType.extensions.findByName("toy") as? ToyExtension

            val content = toyExtension?.content ?: "foo"

            val taskProvider =
                project.tasks.register(variant.name + "AddAsset", AddAssetTask::class.java) {
                    it.content.set(content)
                }

            // 核心部分
            variant.artifacts
                .use(taskProvider)
                .wiredWith(AddAssetTask::outputDir)
                .toAppendTo(MultipleArtifact.ASSETS)
        }

//        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)
//
//        androidComponents.beforeVariants { variantBuilder ->
//            val buildType = android.buildTypes.getByName(variantBuilder.buildType)
//            val toyExtension = buildType.extensions.findByName("toy") as? ToyExtension
//
//            val variantExtension = project.objects.newInstance(ToyVariantExtension::class.java)
//            variantExtension.content.set(toyExtension?.content ?: "foo")
//            variantBuilder.registerExtension(ToyVariantExtension::class.java, variantExtension)
//
//            androidComponents.onVariants { variant ->
//                val content = variant.getExtension(ToyVariantExtension::class.java)?.content
//                val taskProvider =
//                    project.tasks.register(variant.name + "AddAsset", AddAssetTask::class.java) {
//                        it.content.set(content)
//                    }
//
//                // 核心部分
//                variant.artifacts
//                    .use(taskProvider)
//                    .wiredWith(AddAssetTask::outputDir)
//                    .toAppendTo(MultipleArtifact.ASSETS)
//            }
//        }
    }
}