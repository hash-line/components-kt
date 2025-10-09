package text

import Component
import ValidationCode
import Presence

class EmailField(
    id: String = "",
    value: String = "",
    required: Boolean = true,
    readOnly: Boolean = false,
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
    required = required,
    readOnly = readOnly,
    presence = presence,
    singleLine = true,
    placeHolder = placeHolder,
    validation = EmailValidation()
)


/**
 * Validates Email Input. The maximum limit for an email string length should adhere to the RFC 5321 and RFC 5322 standards for email addresses:
 * ## Key Constraints:
 * Local Part (before the @):
 * Can be up to 64 characters.
 * Domain Part (after the @):
 * Can be up to 255 characters.
 * ## Combined Length:
 * The total length of the email address must not exceed 320 characters.
 * However, practical systems often limit the combined length to `254` characters for compatibility and storage reasons (as per the SMTP protocol).
 */
class EmailValidation : TextInputValidation {
    companion object {
        const val MAX_EMAIL_LENGTH = 254

        val INPUT_REGEX = "^[a-zA-Z0-9@._-]*$".toRegex()
        val VALIDATION_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
    }

    override fun validate(input: String): Int {
        return when {
            input.isEmpty() -> ValidationCode.EMPTY // Empty input is invalid.

            input.length > MAX_EMAIL_LENGTH -> ValidationCode.INVALID_LENGTH // Email exceeds maximum length.

            !input.matches(VALIDATION_REGEX) -> ValidationCode.INVALID_FORMAT // Does not match email format.

            // Additional checks for invalid local part
            else -> {
                val atIdx = input.indexOf('@')
                if (atIdx <= 0) return ValidationCode.INVALID_FORMAT
                val local = input.substring(0, atIdx)
                // Local part must not start with dot or contain consecutive dots
                if (local.startsWith(".") || local.contains("..")) {
                    ValidationCode.INVALID_FORMAT
                } else {
                    ValidationCode.VALID
                }
            }
        }
    }
}
