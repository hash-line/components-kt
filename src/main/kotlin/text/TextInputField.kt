package text

import BaseInputField
import Component
import InputField
import InputValidation
import ValidationCode
import Presence


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
abstract class TextInputField(
    enabled: Boolean = true,
    id: String = "",
    value: String = "",
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null,
    required: Boolean = true,
    readOnly: Boolean = false,
    presence: Presence = Presence.Visible,
    validation: TextInputValidation,
    open val singleLine: Boolean = true,
    open val placeHolder: String = "",
    open val label: String = "",
) : BaseInputField<String>(
    id = id,
    value = value,
    presence = presence,
    enabled = enabled,
    top = top,
    bottom = bottom,
    start = start,
    end = end,
    required = required,
    readOnly = readOnly,
    validation = validation
) {

    override suspend fun clear() {
        setValue("")
    }
}


interface TextInputValidation : InputValidation<String>

class TextNotEmptyValidation : TextInputValidation {
    override fun validate(input: String): Int {
        return when {
            input.isEmpty() -> ValidationCode.EMPTY
            else -> ValidationCode.VALID
        }
    }
}
