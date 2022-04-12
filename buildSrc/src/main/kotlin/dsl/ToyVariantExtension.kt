package dsl

import org.gradle.api.provider.Property

/**
 * @author    yiliyang
 * @date      2022/4/12 下午4:22
 * @version   1.0
 * @since     1.0
 */
interface ToyVariantExtension {
    val content: Property<String>
}