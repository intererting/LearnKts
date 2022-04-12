package asset

import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.api.artifact.MultipleArtifact

/**
 * @author    yiliyang
 * @date      2022/4/12 下午2:54
 * @version   1.0
 * @since     1.0
 */
abstract class ToyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)

        androidComponents.onVariants { variant ->
            val taskProvider =
                project.tasks.register(variant.name + "AddAsset", AddAssetTask::class.java) {
                    it.content.set("foo")
                }

            // 核心部分
            variant.artifacts
                .use(taskProvider)
                .wiredWith(AddAssetTask::outputDir)
                .toAppendTo(MultipleArtifact.ASSETS)
        }
    }
}