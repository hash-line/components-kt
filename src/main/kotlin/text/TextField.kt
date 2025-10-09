package text

import Component

import ValidationCode
import Presence

class TextField(
    id: String = "",
    required: Boolean = true,
    readOnly: Boolean = false,
    presence: Presence = Presence.Visible,
    singleLine: Boolean = true,
    placeHolder: String = "",
    label: String = "",
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null,
    value: String = "",
    minLength: Int = 0,
    maxLength: Int = Int.MAX_VALUE,
) : TextInputField(
    id = id,
    top = top,
    value = value,
    bottom = bottom,
    start = start,
    end = end,
    required = required,
    presence = presence,
    readOnly = readOnly,
    singleLine = singleLine,
    placeHolder = placeHolder,
    label = label,
    validation = TextValidation(
        minLength = minLength,
        maxLength = maxLength
    )
)

/**
 * ## Combined Length:
 * The maximum length of an IBAN (International Bank Account Number) is 34 characters,
 * although the actual length can vary depending on the country's domestic banking system
 */

class TextValidation(
    private val minLength: Int,
    private val maxLength: Int
) : TextInputValidation {

    override fun validate(input: String): Int {
        return when {
            input.isEmpty() -> ValidationCode.EMPTY
            input.length !in minLength..maxLength -> ValidationCode.INVALID_LENGTH
            else -> ValidationCode.VALID
        }
    }
}
