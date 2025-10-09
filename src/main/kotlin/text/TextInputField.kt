package text

import BaseInputField
import InputField
import InputValidation
import ValidationCode
import kotlinx.serialization.Serializable

interface TextInputValidation : InputValidation<String>

@Serializable
class TextNotEmptyValidation : TextInputValidation {
    override fun validate(input: String): Int {
        return when {
            input.isEmpty() -> ValidationCode.EMPTY
            else -> ValidationCode.VALID
        }
    }
}

/**
 * [TextInputField] represents an input field, where user can input data in form of text.
 * For example, name, email, phone number, etc.
 *
 * @property id Unique identifier of the input field.
 * @property required Whether the input field is required, defaults is true.
 * @property readOnly Whether the input field is read-only, default is false.
 * @property singleLine Whether the input field is single line, default is true.
 * @property validation The validation rules for the input field.
 *
 * @see InputField
 * @see InputValidation
 */
abstract class TextInputField() : BaseInputField<String>() {

    abstract val singleLine: Boolean

    abstract val placeHolder: String

    abstract val label: String

    override val default: String
        get() = ""

    override suspend fun clear() {
        setValue("")
    }
}
