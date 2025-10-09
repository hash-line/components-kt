package text

import Component
import ValidationCode
import Presence


class PinField(
    id: String = "",
    value: String = "",
    required: Boolean = true,
    readOnly: Boolean = false,
    presence: Presence = Presence.Visible,
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null,
    val length: Int = 4,
) : TextInputField(
    id = id,
    value = value,
    top = top,
    bottom = bottom,
    start = start,
    end = end,
    required = required,
    readOnly = readOnly,
    presence = presence,
    singleLine = true,
    placeHolder = "",
    validation = PinValidation(length = length)
)

class PinValidation(
    private val length: Int = 4
) : TextInputValidation {
    override fun validate(input: String): Int {
        return when {
            input.isEmpty() -> ValidationCode.EMPTY
            input.length != length -> ValidationCode.INVALID_LENGTH
            !input.all { it.isDigit() } -> ValidationCode.INVALID_FORMAT
            else -> ValidationCode.VALID
        }
    }
}