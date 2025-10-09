/**
 * Represents a validation rule for an input.
 *
 */
interface InputValidation<T> {

    /**
     * Validates the given input.
     *
     * @return Int representing [ValidationCode].
     *
     * @see ValidationCode
     */
    fun validate(input: T): Int
}

/**
 * Validation codes to be used in [InputValidation] to represent the validation status
 */
object ValidationCode {
    /**
     * Input is valid
     */
    const val VALID = 10

    /**
     * Input is null
     */
    const val UNDEFINED = 11

    /**
     * Input is empty
     */
    const val EMPTY = 12

    /**
     * Invalid length of input
     */
    const val INVALID_LENGTH = 13

    /**
     * Invalid format of input
     */
    const val INVALID_FORMAT = 14

    /**
     * Invalid format of input
     */
    const val INCORRECT_ORDER = 15

    fun toString(code: Int): String = when (code) {
        VALID -> "VALID"
        UNDEFINED -> "NULL"
        EMPTY -> "EMPTY"
        INVALID_LENGTH -> "INVALID_LENGTH"
        INVALID_FORMAT -> "INVALID_FORMAT"
        INCORRECT_ORDER -> "INCORRECT_ORDER"
        else -> "UNKNOWN"
    }
}


class NoValidation<T> : InputValidation<T> {
    override fun validate(input: T): Int = ValidationCode.VALID
}
