package text

import Component
import ValidationCode
import Presence
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * [NameField] is an [text.TextInputField] where user can input name. A name is between 3 - 20 chars length, and
 * cannot contain any numbers.
 *
 * @property splitName Whether the name should be split into first and last name.
 */
class NameField(
    id: String = "Name",
    required: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    presence: Presence = Presence.Visible,
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null,
    placeHolder: String = "",
    value: String = "",
    val splitName: Boolean = false
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
    singleLine = singleLine,
    placeHolder = placeHolder,
    validation = NameValidation(
        splitName = splitName
    )
) {
    private val _firstName = MutableStateFlow<String>("")
    val firstName: StateFlow<String> = _firstName

    private val _lastName = MutableStateFlow<String>("")
    val lastName: StateFlow<String> = _lastName

    suspend fun setFirstName(firstName: String) {
        _firstName.value = firstName
        setValue("$firstName ${lastName.value}")
    }

    suspend fun setLastName(lastName: String) {
        _lastName.value = lastName
        setValue("${firstName.value} $lastName")
    }
}

class NameValidation(
    val splitName: Boolean
) : TextInputValidation {

    companion object {
        val MIN_LENGTH = 3
        val MAX_LENGTH = 40
    }

    override fun validate(input: String): Int {
        return when {
            input.isEmpty() -> ValidationCode.EMPTY
            input.length < MIN_LENGTH || input.length > MAX_LENGTH -> ValidationCode.INVALID_LENGTH
            splitName && input.split(" ").let {
                it.size < 2 || it.any { namePart -> namePart.length < MIN_LENGTH || namePart.length > MAX_LENGTH / 2 }
            } -> ValidationCode.INVALID_FORMAT
            input.any { it.isDigit() } -> ValidationCode.INVALID_FORMAT
            else -> ValidationCode.VALID
        }
    }
}
