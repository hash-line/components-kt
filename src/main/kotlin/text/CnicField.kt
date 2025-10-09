package text

import Component
import ValidationCode
import Presence

class CnicField(
    id: String = "CNIC",
    value: String = "",
    required: Boolean = true,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    presence: Presence = Presence.Visible,
    placeHolder: String = "",
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null
) : TextInputField(
    id = id,
    value = value,
    top = top,
    bottom = bottom,
    start = start,
    end = end,
    enabled = enabled,
    required = required,
    readOnly = readOnly,
    presence = presence,
    singleLine = true,
    placeHolder = placeHolder,
    validation = CnicValidation()
)


/**
 * Validates CNIC Input. CNIC is 13 digit number
 */
class CnicValidation : TextInputValidation {
    override fun validate(input: String): Int {
        return when {
            input.isEmpty() -> ValidationCode.EMPTY
            input.length != 13 -> ValidationCode.INVALID_LENGTH
            !input.matches(Regex("^\\d{13}\$")) -> ValidationCode.INVALID_FORMAT
            else -> ValidationCode.VALID
        }
    }
}
