package text

import Component
import ValidationCode
import Presence
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [NameField] is an [text.TextInputField] where user can input name. A name is between 3 - 20 chars length, and
 * cannot contain any numbers.
 *
 * @property splitName Whether the name should be split into first and last name.
 */
@Serializable
class NameField(
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
    val splitName: Boolean = false
) : TextInputField(
) {
    override val validation = NameValidation(splitName = splitName)

    @kotlinx.serialization.Transient
    private val _firstName = MutableStateFlow<String>("")
    val firstName: StateFlow<String> = _firstName

    @kotlinx.serialization.Transient
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

@Serializable
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
