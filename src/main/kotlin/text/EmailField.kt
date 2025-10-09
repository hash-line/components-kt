package text

import Component
import ValidationCode
import Presence
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class EmailField(
    override val id: String = "",
    override val required: Boolean = true,
    override val readOnly: Boolean = false,
    override val placeHolder: String = "",
    override val label: String = "",
    override val top: Component? = null,
    override val bottom: Component? = null,
    override val start: Component? = null,
    override val end: Component? = null,
    @SerialName("value") private val value: String = "",
    @SerialName("presence") private val presence: Presence = Presence.Visible,
    @SerialName("enabled") private val enabled: Boolean = true,
) : TextInputField(){

    override val validation = EmailValidation()

    override val singleLine: Boolean = true

    init {
        setValueSilently(value)
        setPresence(presence)
        setEnabled(enabled)
    }
}


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
@Serializable
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
