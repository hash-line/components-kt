package selection

import BaseComponent
import Component
import InputValidation
import ValidationCode
import Presence
import kotlinx.datetime.Clock
import kotlin.time.ExperimentalTime

class DateRangeItem(
    id: String = "",
    val start: Long,
    val end: Long,
    val format: String
) : BaseComponent(
    id = id
)

class DateRangeField(
    id: String = "",
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null,
    required: Boolean = true,
    value: DateRangeItem? = null,
    fieldPresence: Presence = Presence.Visible,
    validation: DateRangeValidation = DateRangeValidation(DateRanges.indefinite),
    val selectionMode: DateSelectionMode = DateSelectionMode.Range,
    val constraints: DateSelectionConstraint = DateConstraints.none,
    val labelFirst: String = "",
    val labelSecond: String = "",
    val format: String,
) : SelectionInputField(
    id = id,
    value = value,
    top = top,
    bottom = bottom,
    start = start,
    end = end,
    required = required,
    fieldPresence = fieldPresence,
    validation = validation,
    options = emptyList<Component>()
)


/**
 * [DateRanges] provides predefined date ranges in epoch milliseconds.
 */
object DateRanges {
    val indefinite: Long
        get() = Long.MAX_VALUE

    val oneDay: Long
        get() = 24 * 60 * 60 * 1000L

    val oneWeek: Long
        get() = 7 * oneDay

    val oneMonth: Long
        get() = 30 * oneDay

    val threeMonths: Long
        get() = 3 * oneMonth

    val sixMonths: Long
        get() = 6 * oneMonth

    val oneYear: Long
        get() = 365 * oneDay
}

/**
 * [DateRangeValidation] validates that the date range is within the specified range in epoch milliseconds.
 *
 * @property rangeInMillis The allowed range in epoch milliseconds.
 */
class DateRangeValidation(
    private val rangeInMillis: Long
) : InputValidation<Component?> {
    override fun validate(input: Component?): Int {
        if (input !is DateRangeItem) {
            return ValidationCode.INVALID_FORMAT
        }

        val startDate = input.start
        val endDate = input.end

        // Check if end date is before start date
        if (endDate < startDate) {
            return ValidationCode.INCORRECT_ORDER
        }
        return if (endDate - startDate <= rangeInMillis) {
            ValidationCode.VALID
        } else {
            ValidationCode.INVALID_FORMAT
        }
    }
}

/**
 * [DateSelectionConstraint] defines the constraints for date selection.
 *
 * @property minDate The minimum date that can be selected. Defaults to null, which means no minimum date.
 * @property maxDate The maximum date that can be selected. Defaults to null, which means no maximum date.
 */
data class DateSelectionConstraint(
    val minDate: Long? = null,
    val maxDate: Long? = null
)

/**
 * [DateConstraints] provides predefined date constraints.
 */
object DateConstraints {
    val none: DateSelectionConstraint
        get() = DateSelectionConstraint()

    @OptIn(ExperimentalTime::class)
    val lastOneMonth: DateSelectionConstraint
        get() = DateSelectionConstraint(
            minDate = Clock.System.now().toEpochMilliseconds() - DateRanges.oneMonth,
            maxDate = Clock.System.now().toEpochMilliseconds()
        )

    @OptIn(ExperimentalTime::class)
    val lastThreeMonths: DateSelectionConstraint
        get() = DateSelectionConstraint(
            minDate = Clock.System.now().toEpochMilliseconds() - DateRanges.threeMonths,
            maxDate = Clock.System.now().toEpochMilliseconds()
        )

    @OptIn(ExperimentalTime::class)
    val lastSixMonths: DateSelectionConstraint
        get() = DateSelectionConstraint(
            minDate = Clock.System.now().toEpochMilliseconds() - DateRanges.sixMonths,
            maxDate = Clock.System.now().toEpochMilliseconds()
        )

    fun custom(minDate: Long, maxDate: Long): DateSelectionConstraint {
        return DateSelectionConstraint(minDate = minDate, maxDate = maxDate)
    }
}


enum class DateSelectionMode {
    Range,
    Single
}