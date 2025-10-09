package text

import Component
import ValidationCode
import Presence

class AmountField(
    id: String = "Amount",
    value: String = "",
    required: Boolean = true,
    readOnly: Boolean = false,
    presence: Presence = Presence.Visible,
    placeHolder: String = "",
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null,
    minAmount: Double = 0.0,
    maxAmount: Double = Double.MAX_VALUE
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
    validation = AmountValidation(
        minAmount = minAmount,
        maxAmount = maxAmount
    )
)

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
