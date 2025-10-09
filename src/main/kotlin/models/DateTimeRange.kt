package models

import TimeFrame
import kotlinx.serialization.Serializable

@Serializable
data class DateTimeRange(
    val start: Long,
    val end: Long
) {
    fun isWithin(timeStamp: Long): Boolean {
        return timeStamp in start..end
    }
}

@Serializable
data class DateTimePickerConstraints(
    val minDate: Long,
    val maxDate: Long
)

@Serializable
data class DateTimeRangePickerConstraints(
    val minRange: TimeFrame? = null,
    val maxRange: TimeFrame? = null,
    val allowFutureDates: Boolean = true,
    val allowPastDates: Boolean = true
)
