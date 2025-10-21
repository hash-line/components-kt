package selection

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

/**
 * SelectionInputModule defines the polymorphic serialization scope for all SelectionInputField implementations.
 * This module allows Kotlin Serialization to properly serialize and deserialize different types
 * of selection input fields polymorphically.
 */
val SelectionInputModule = SerializersModule {
    polymorphic(SelectionInputField::class) {
        subclass(DropDownField::class)
        subclass(MultiChoiceField::class)
        subclass(AcknowledgementField::class)
        subclass(DateRangeField::class)
    }
    
    // Also include validation classes
    polymorphic(InputValidation::class) {
        subclass(ItemNotNullValidation::class)
    }
}