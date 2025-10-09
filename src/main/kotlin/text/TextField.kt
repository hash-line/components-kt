package text

import Component
import Presence
import ValidationCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TextField(
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
    @SerialName("minLength") val minLength: Int = 0,
    @SerialName("maxLength") val maxLength: Int = Int.MAX_VALUE,
    @SerialName("presence") private val presence: Presence = Presence.Visible,
    @SerialName("enabled") private val enabled: Boolean = true
) : TextInputField(
    validation = TextValidation(
        minLength = minLength,
        maxLength = maxLength
    )
){
    init {
        setValueSilently(value)
        setPresence(presence)
        setEnabled(enabled)
    }
}

/**
 * ## Combined Length:
 * The maximum length of an IBAN (International Bank Account Number) is 34 characters,
 * although the actual length can vary depending on the country's domestic banking system
 */

@Serializable
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
