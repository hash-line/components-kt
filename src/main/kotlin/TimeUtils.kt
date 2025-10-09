import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime


/**
 * This object holds predefined date-time format patterns that developers can use.
 *
 * The goal is to provide commonly used formats in a consistent manner, avoiding
 * potential mistakes in format definitions across the project.
 */
object DateTimeFormats {

    /**
     * Format: Day in single-digit or double-digit format, short month name, and full year.
     * Example: "5 Sep, 2024"
     */
    const val d_MMM_yyyy: String = "d MMM, yyyy"

    /**
     * Format: Day, full month name, full year, and time in 12-hour format with AM/PM.
     * Example: "5 September, 2024 | 8:30 AM"
     */
    const val d_MMM_yyyy_h_mm_a: String = "d MMMM, yyyy | h:mm a"

    /**
     * Format: Full date with year, month, and day in numeric format.
     * Example: "2024-09-05"
     */

    const val yyyy_mm_dd: String = "yyyy-MM-dd"

    /**
     * Format: Full date with year, month, and day in numeric format.
     * Example: "05 / 09 / 2024"
     */
    const val dd_mm_yyyy: String = "dd / MM / yyyy"

    const val ddMMyyyy: String = "ddMMyyyy"


    /**
     * Format: Full date with year, month, day, and time in 24-hour format.
     * Example: "2024-09-05 14:30:00"
     */
    const val yyyy_MM_dd_HH_mm_ss: String = "yyyy-MM-dd HH:mm:ss"

    /**
     * Format: Full date with day, month, year, and time in 12-hour format with AM/PM.
     * Example: "5 September 2024, 8:30 PM"
     */
    const val d_MMMM_yyyy_hh_mm_a: String = "d MMMM yyyy, hh:mm a"

    /**
     * Format: Day and short month name.
     * Example: "5 Sep"
     */
    const val d_MMM: String = "d MMM"

    /**
     * Format: Day, full month name, and full year.
     * Example: "5 September 2024"
     */
    const val d_MMMM_yyyy: String = "d MMMM yyyy"

    /**
     * Format: Time in 12-hour format with AM/PM.
     * Example: "8:30 AM"
     */
    const val h_mm_a: String = "h:mm a"


    /**
     * Format: Time in 12-hour format with AM/PM.
     * Example: "08:30:11 AM"
     */
    const val hh_mm_MM_a: String = "hh:mm:MM a"
}

// Convert a date string to milliseconds (only supports ISO 8601 in commonMain)
@OptIn(ExperimentalTime::class)
fun String.toEpochMillis(dateFormat: String? = "dd MMMM, yyyy"): Long? {
    return try {
        // Only ISO 8601 supported in commonMain
        Instant.parse(this).toEpochMilliseconds()
    } catch (e: Exception) {
        null
    }
}

// Converts a date string from one format to another (only supports ISO 8601 in commonMain)
@OptIn(ExperimentalTime::class)
fun String.convertDateFormat(
    inputFormat: String,
    outputFormat: String,
    locale: Any? = null // Locale is not supported in commonMain
): String {
    return try {
        // Only ISO 8601 supported in commonMain
        val instant = Instant.parse(this)
        // Only basic ISO output
        instant.toString()
    } catch (e: Exception) {
        ""
    }
}

// Convert milliseconds to formatted date string (only ISO 8601 in commonMain)
@OptIn(ExperimentalTime::class)
fun Long.toFormattedDateTime(dateFormat: String = "dd MMMM, yyyy"): String {
    return try {
        val instant = Instant.fromEpochMilliseconds(this)
        val year = instant.toLocalDateTime(TimeZone.UTC).year
        if (year < 1970 || year > 2100) "" else instant.toString()
    } catch (e: Exception) {
        ""
    }
}

/**
 * Offsets the given epoch time in milliseconds by specified time units.
 */
@OptIn(ExperimentalTime::class)
fun Long.offset(
    millis: Int = 0,
    seconds: Int = 0,
    minutes: Int = 0,
    hours: Int = 0,
    days: Int = 0,
    months: Int = 0,
    years: Int = 0
): Long {
    var instant = Instant.fromEpochMilliseconds(this)
    val timeZone = TimeZone.UTC

    if (millis != 0) instant = instant.plus(millis.toLong(), DateTimeUnit.MILLISECOND)
    if (seconds != 0) instant = instant.plus(seconds.toLong(), DateTimeUnit.SECOND)
    if (minutes != 0) instant = instant.plus(minutes.toLong(), DateTimeUnit.MINUTE)
    if (hours != 0) instant = instant.plus(hours.toLong(), DateTimeUnit.HOUR)

    if (days != 0 || months != 0 || years != 0) {
        val localDateTime = instant.toLocalDateTime(timeZone)

        // Use DatePeriod instead of DateTimePeriod
        val datePeriod = DatePeriod(years, months, days)
        val newDate = localDateTime.date.plus(datePeriod)
        // Preserve original time component
        val newDateTime = LocalDateTime(newDate, localDateTime.time)

        instant = newDateTime.toInstant(timeZone)
    }
    return instant.toEpochMilliseconds()
}

/**
 * Time units for rounding epoch timestamps
 */
enum class TimeUnit {
    YEAR,
    MONTH,
    DAY,
    HOUR,
    MINUTE,
    SECOND
}

@Serializable
data class TimeFrame(
    val unit : TimeUnit,
    val value: Int
)

fun Int.months(): TimeFrame = TimeFrame(
    unit = TimeUnit.MONTH,
    value = this
)

/**
 * Rounds an epoch timestamp to the specified time unit by resetting all smaller units.
 */
@OptIn(ExperimentalTime::class)
fun Long.roundOff(unit: TimeUnit): Long {
    val dt = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC)
    val rounded = when (unit) {
        TimeUnit.YEAR -> LocalDateTime(dt.year, 1, 1, 0, 0, 0, 0)
        TimeUnit.MONTH -> LocalDateTime(dt.year, dt.month, 1, 0, 0, 0, 0)
        TimeUnit.DAY -> LocalDateTime(dt.year, dt.month, dt.dayOfMonth, 0, 0, 0, 0)
        TimeUnit.HOUR -> LocalDateTime(dt.year, dt.month, dt.dayOfMonth, dt.hour, 0, 0, 0)
        TimeUnit.MINUTE -> LocalDateTime(dt.year, dt.month, dt.dayOfMonth, dt.hour, dt.minute, 0, 0)
        TimeUnit.SECOND -> LocalDateTime(dt.year, dt.month, dt.dayOfMonth, dt.hour, dt.minute, dt.second, 0)
    }
    return rounded.toInstant(TimeZone.UTC).toEpochMilliseconds()
}

// Convert milliseconds to formatted time string
fun Long.toTimeString(): String {
    val minutes = this / 60000
    val seconds = (this % 60000) / 1000
    val milliseconds = this % 1000

    return when {
        this < 1000 -> "$milliseconds ms"
        this < 60000 -> "$seconds sec $milliseconds ms"
        else -> "$minutes min $seconds sec $milliseconds ms"
    }
}

// Only supports ISO 8601 input in commonMain
@OptIn(ExperimentalTime::class)
fun String.toTimeInMillis(): Long? {
    return try {
        val instant = Instant.parse(this)
        val dt = instant.toLocalDateTime(TimeZone.UTC)
        val hoursInMillis = dt.hour * 60 * 60 * 1000L
        val minutesInMillis = dt.minute * 60 * 1000L
        val secondsInMillis = dt.second * 1000L
        hoursInMillis + minutesInMillis + secondsInMillis
    } catch (e: Exception) {
        null
    }
}