package text

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

/**
 * TextInputModule defines the polymorphic serialization scope for all TextInputField implementations.
 * This module allows Kotlin Serialization to properly serialize and deserialize different types
 * of text input fields polymorphically.
 */
val TextInputModule = SerializersModule {
    polymorphic(TextInputField::class) {
        subclass(TextField::class)
        subclass(EmailField::class)
        subclass(NameField::class)
        subclass(NumberField::class)
        subclass(PinField::class)
        subclass(OtpField::class)
        subclass(MsisdnField::class)
        subclass(DateField::class)
        subclass(CnicField::class)
        subclass(AmountField::class)
    }
    
    // Also include validation classes
    polymorphic(TextInputValidation::class) {
        subclass(TextValidation::class)
        subclass(TextNotEmptyValidation::class)
        subclass(EmailValidation::class)
        subclass(NameValidation::class)
        subclass(PinValidation::class)
        subclass(OTPValidation::class)
        subclass(MsisdnValidation::class)
        subclass(DateValidation::class)
        subclass(CnicValidation::class)
        subclass(AmountValidation::class)
    }
}