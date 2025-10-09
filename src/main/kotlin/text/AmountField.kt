package text

import Component
import ValidationCode
import Presence
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AmountField(
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
    @SerialName("minAmount") val minAmount: Double = 0.0,
    @SerialName("maxAmount") val maxAmount: Double = Double.MAX_VALUE,
    @SerialName("presence") private val presence: Presence = Presence.Visible,
    @SerialName("enabled") private val enabled: Boolean = true
) : TextInputField(){

    override val validation = AmountValidation(
        minAmount = minAmount,
        maxAmount = maxAmount
    )
}

@Serializable
class AmountValidation(
    private val minAmount: Double = 0.0,
    private val maxAmount: Double = Double.MAX_VALUE
) : TextInputValidation {

    companion object {
        const val MIN_LENGTH = 1
        const val MAX_LENGTH = 12
    }

    override fun validate(input: String): Int {
        if (input.isEmpty()) return ValidationCode.EMPTY

        // First, check if input is a valid number
        val amount = input.toDoubleOrNull() ?: return ValidationCode.INVALID_FORMAT

        // Then, check length constraints
        if (input.length !in MIN_LENGTH..MAX_LENGTH) return ValidationCode.INVALID_LENGTH

        // Now, check amount range (inclusive)
        if (amount < minAmount || amount > maxAmount) return ValidationCode.INVALID_LENGTH

        return ValidationCode.VALID
    }
}
