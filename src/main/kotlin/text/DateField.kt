package text

import Component
import Presence
import ValidationCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime

@Serializable
class DateField(
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
    @SerialName("presence") private val presence: Presence = Presence.Visible,
    @SerialName("enabled") private val enabled: Boolean = true,
    val format: String = "ddMMyyyy",
) : TextInputField(
    validation = DateValidation(format = format)
) {
    suspend fun setValue(value: Long) {
        //setValue(value.toFormattedDateTime(format))
    }
}


/**
 * Date Validation
 */
@Serializable
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
