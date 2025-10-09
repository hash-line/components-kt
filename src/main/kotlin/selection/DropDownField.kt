package selection

import Component
import InputValidation
import Presence
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * [DropDownField] is a [selection.SelectionInputField] where user can select one option from a list. In [DropDownField] field all available options are not
 * visible, when user interacts with the field only than the available options are shown. e.g. dropdown field for selecting purpose of payment.
 *
 * @property placeHolder The text to display when no option is selected.
 * @property optionsLabel The title to display above the dropdown.
 *
 * @see selection.SelectionInputField
 * @see InputValidation
 */
class DropDownField(
    itemId: String? = null,
    enabled: Boolean = true,
    required: Boolean = true,
    id: String = "",
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null,
    readOnly: Boolean = false,
    options: List<Component> = emptyList(),
    optionsLabel: String = "",
    searchable: Boolean = false,
    presence: Presence = Presence.Visible,
    val placeHolder: String = "",
) : SelectionInputField(
    id = id,
    top = top,
    enabled = enabled,
    bottom = bottom,
    start = start,
    end = end,
    required = required,
    readOnly = readOnly,
    fieldPresence = presence,
    options = options,
    value = options.firstOrNull { it.id == itemId },
    validation = ItemNotNullValidation()
) {
    private val _optionsLabel = MutableStateFlow(optionsLabel)
    val optionsLabel: StateFlow<String>
        get() = _optionsLabel

    suspend fun setOptions(optionsLabel: String, options: List<Component>, selectedIndex: Int = -1) {
        setOptions(options, selectedIndex)
        _optionsLabel.value = optionsLabel
    }

}