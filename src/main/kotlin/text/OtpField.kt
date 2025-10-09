package text

import Component
import ValidationCode
import Presence
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * OTP fields are used for one time password. These fields are readonly by default
 *
 * @property isAlphanumeric Whether the OTP should be alphanumeric or numeric
 * @property length The length of the OTP
 */
@Serializable
class OtpField(
    override val id: String = "",
    override val required: Boolean = true,
    override val readOnly: Boolean = false,
    override val singleLine: Boolean = true,
    override val placeHolder: String = "",
    override val label: String = "",
    override val top: Component? = null,
    override val bottom: Component? = null,
    override val start: Component? = null,
    override val end: Component? = null,
    @SerialName("value") private val value: String = "",
    @SerialName("length") private val length: Int = 4,
    @SerialName("presence") private val presence: Presence = Presence.Visible,
    @SerialName("enabled") private val enabled: Boolean = true,
    val isAlphanumeric: Boolean,
    val autoFetched: Boolean = true
) : TextInputField(
    validation = OTPValidation(
        isAlphanumeric = isAlphanumeric,
        length = length
    )
)


@Serializable
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
