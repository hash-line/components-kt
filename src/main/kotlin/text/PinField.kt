package text

import Component
import ValidationCode
import Presence
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class PinField(
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
    @SerialName("enabled") private val enabled: Boolean = true
) : TextInputField(
    validation = PinValidation(length = length)
)

@Serializable
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