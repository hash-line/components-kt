import groups.BaseLabeledComponent
import groups.LabeledComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable


/**
 * [InputField] represents an input field, where user can enter some data.
 *
 * @property id Unique identifier of the input field.
 * @property top Decorates the top of the input field.
 * @property bottom Decorates the bottom of the input field.
 * @property start Decorates the start (left) of the input field.
 * @property end Decorates the end (right) of the input field.
 * @property required Whether input is required or not. In case not required, then validation ignore input constraints are ignored in case of empty
 * @property readOnly Whether input is read only or not. Read only fields cannot be modified by user
 * @property enabledFlow Whether input is enabled or not. Enabled fields can be modified by user
 * @property validationStateFlow [ValidationCode] representing state of the field. validation is dependent on the value and the applied validation if any.
 * @property valueFlow The value of the input field.
 *
 * @see InputValidation
 * @see ValidationCode
 */
interface InputField<T> : LabeledComponent {

    val required: Boolean

    val readOnly: Boolean

    val validationStateFlow: StateFlow<Int>

    val validation: InputValidation<T>

    val valueFlow: StateFlow<T>

    val default: T

    suspend fun setValue(value: T)

    suspend fun clear()
}


/**
 * [BaseInputField] is parent class for all input fields.
 */
@Serializable
abstract class BaseInputField<T>() : BaseLabeledComponent(), InputField<T> {

    private val _value = MutableStateFlow(default)
    override val valueFlow: StateFlow<T>
        get() = _value

    private val _validationState = MutableStateFlow(validateInput(default))
    override val validationStateFlow: StateFlow<Int>
        get() = _validationState


    protected fun setValueSilently(value: T) {
        _value.value = value
        _validationState.value = if (presenceFlow.value == Presence.Forgotten)
            ValidationCode.VALID
        else
            validateInput(value)
    }

    override suspend fun setValue(value: T) {
        _value.value = value
        _validationState.value = if (presenceFlow.value == Presence.Forgotten)
            ValidationCode.VALID
        else
            validateInput(value)

        raiseEvent(
            InputFieldValueChangeEvent(
                value = value,
                field = this,
                validationState = _validationState.value
            )
        )
    }

    /**
     * Updates the presence of the form field to the specified [Presence].
     *
     * This allows the form to dynamically control whether the field is visible, hidden, or forgotten (ignored in form submissions).
     *
     * @param newPresence The new presence state for the form field.
     */
    override fun setPresence(newPresence: Presence) {
        super.setPresence(newPresence)
        if (newPresence == Presence.Forgotten) {
            _validationState.value = ValidationCode.VALID
        }
    }


    private fun validateInput(value: T): Int {
        val validationState = validation.validate(value)
        return if (!required && (validationState == ValidationCode.EMPTY || validationState == ValidationCode.UNDEFINED)) ValidationCode.VALID else validationState
    }

}






