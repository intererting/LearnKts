package start/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.gradle.api.Project
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin

/**
 * 通过AndroidComponentsExtension添加新的buildType
 */
abstract class MyPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val androidComponents =
            project.extensions.getByType(AndroidComponentsExtension::class.java) as AndroidComponentsExtension<*, *, *>
        androidComponents.finalizeDsl { extension ->
            extension.buildTypes.create("owner").let {
                it.isJniDebuggable = true
            }
        }
    }
}
