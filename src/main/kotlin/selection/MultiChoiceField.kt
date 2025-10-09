package selection

import Orientation
import Component
import Presence
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * [MultiChoiceField] is a [selection.SelectionInputField] where user is shown a number of options and user has to pick one. This
 * is different from [DropDownField] because the options are always visible to the user. Whereas in [DropDownField] the options are visible
 * only when user interacts with the field. This is useful in cases where the list of options is small e.g. Question with Yes, No answers
 *
 * @property choiceLabel The Label decoration
 */
class MultiChoiceField(
    id: String = "",
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null,
    required: Boolean = true,
    readOnly: Boolean = false,
    itemId: String? = null,
    options: List<Component>,
    choiceLabel: Component? = null,
    presence: Presence = Presence.Visible,
    val orientation: Orientation = Orientation.Vertical
) : SelectionInputField(
    id = id,
    top = top,
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
    private val _choiceLabel = MutableStateFlow(choiceLabel)
    val choiceLabel: StateFlow<Component?>
        get() = _choiceLabel

    suspend fun setChoices(
        choiceLabel: Component? = null,
        choices: List<Component>,
        selectedIndex: Int = -1
    ) {
        setOptions(choices, selectedIndex)
        _choiceLabel.value = choiceLabel
    }
}