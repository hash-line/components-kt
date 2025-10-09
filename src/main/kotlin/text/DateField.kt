package text

import Component
import Presence
import ValidationCode
import kotlin.time.ExperimentalTime

class DateField(
    id: String = "",
    value: String = "",
    required: Boolean = true,
    readOnly: Boolean = false,
    presence: Presence = Presence.Visible,
    placeHolder: String = "",
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null,
    val format: String = "ddMMyyyy",
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
    validation = DateValidation(format = format)
) {
    suspend fun setValue(value: Long) {
        //setValue(value.toFormattedDateTime(format))
    }
}


/**
 * Date Validation
 */
class DateValidation(
    private val format: String = "dd/MM/yyyy",
    private val allowFutureDates: Boolean = true
) : TextInputValidation {

    @OptIn(ExperimentalTime::class)
    override fun validate(input: String): Int {
        if (input.isEmpty()) return ValidationCode.EMPTY

//        val dateMillis = input.toEpochMillis(format)
//        if (dateMillis == null) return ValidationCode.INVALID_FORMAT
//
//        val now = Clock.System.now().toEpochMilliseconds()
//
//        // Validate future dates
//        if (!allowFutureDates && dateMillis > now) {
//            return ValidationCode.INVALID_FORMAT
//        }
        return ValidationCode.VALID
    }
}
