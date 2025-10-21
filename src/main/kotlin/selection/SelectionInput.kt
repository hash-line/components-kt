package selection

import BaseInputField
import Component
import InputField
import InputValidation
import ValidationCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

/**
 * [SelectionInputField] is an [InputField] where user selects one or more options from a list of options.
 * [SelectionInputField] can be single selection where user can select only one option e.g dropdown fields for selecting payment options or
 * multiple-selection where user can select multiple options. e.g. start and end date for a date range
 *
 * @see InputField
 */
@Serializable
abstract class SelectionInputField() : BaseInputField<Component?>() {

    override val default: Component?
        get() = null

    @kotlinx.serialization.Transient
    private val _options = MutableStateFlow<List<Component>>(emptyList())
    val optionsFlow: StateFlow<List<Component>>
        get() = _options

    override suspend fun clear() {
        setValue(null)
    }

    suspend fun setValue(index: Int) {
        val options = _options.value
        if (options.isEmpty() || index < 0 || index >= options.size) return
        setValue(_options.value[index])
    }

    suspend fun setValue(itemId: String) {
        val options = _options.value
        val item = options.firstOrNull { it.id == itemId }
        if (item != null) {
            setValue(item)
        }
    }

    suspend fun setOptions(options: List<Component>, selectedIndex: Int = -1) {
        _options.value = options
        if (selectedIndex != -1) {
            setValue(options[selectedIndex])
        } else {
            setValue(null)
        }
    }
}


/**
 * Validates that input is not null
 */
@Serializable
class ItemNotNullValidation : InputValidation<Component?> {
    override fun validate(input: Component?): Int =
        if (input == null) ValidationCode.UNDEFINED else ValidationCode.VALID
}

