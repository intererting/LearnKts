package asset

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * @author    yiliyang
 * @date      2022/4/12 下午2:52
 * @version   1.0
 * @since     1.0
 */
abstract class AddAssetTask : DefaultTask() {
    @get:Input
    abstract val content: Property<String>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun taskAction() {
        File(outputDir.asFile.get(), "extra.txt").writeText(content.get())
    }
}