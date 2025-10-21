package media

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

/**
 * MediaInputModule defines the polymorphic serialization scope for all MediaField implementations.
 * This module allows Kotlin Serialization to properly serialize and deserialize different types
 * of media input fields polymorphically.
 */
val MediaInputModule = SerializersModule {
    polymorphic(MediaField::class) {
        subclass(ImageField::class)
        subclass(VideoField::class)
        subclass(DocumentField::class)
    }
    
    // Also include validation classes
    polymorphic(FileInputValidation::class) {
        subclass(DocumentFileValidation::class)
        subclass(ImageFileValidation::class)
        subclass(VideoFileValidation::class)
    }
}