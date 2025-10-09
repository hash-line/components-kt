package text

import Component
import ValidationCode
import Presence

/**
 * OTP fields are used for one time password. These fields are readonly by default
 *
 * @property isAlphanumeric Whether the OTP should be alphanumeric or numeric
 * @property length The length of the OTP
 */
class OtpField(
    id: String = "",
    value: String = "",
    required: Boolean = true,
    presence: Presence = Presence.Visible,
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null,
    val isAlphanumeric: Boolean,
    val length: Int = 4,
    val autoFetched: Boolean = true
) : TextInputField(
    id = id,
    value = value,
    top = top,
    bottom = bottom,
    start = start,
    end = end,
    required = required,
    readOnly = autoFetched,
    presence = presence,
    singleLine = true,
    placeHolder = "",
    validation = OTPValidation(
        isAlphanumeric = isAlphanumeric,
        length = length
    )
)



class OTPValidation(
    private val isAlphanumeric: Boolean = false,
    private val length: Int = 4
) : TextInputValidation {
    override fun validate(input: String): Int {
        return when {
            input.isEmpty() -> ValidationCode.EMPTY
            input.length != length -> ValidationCode.INVALID_LENGTH
            isAlphanumeric && !input.matches(Regex("^[A-Za-z0-9]{$length}\$")) -> ValidationCode.INVALID_FORMAT
            !isAlphanumeric && !input.matches(Regex("^\\d{$length}\$")) -> ValidationCode.INVALID_FORMAT
            else -> ValidationCode.VALID
        }
    }
}
