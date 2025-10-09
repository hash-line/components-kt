package selection

import Orientation
import Component
import Presence
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [MultiChoiceField] is a [selection.SelectionInputField] where user is shown a number of options and user has to pick one. This
 * is different from [DropDownField] because the options are always visible to the user. Whereas in [DropDownField] the options are visible
 * only when user interacts with the field. This is useful in cases where the list of options is small e.g. Question with Yes, No answers
 *
 * @property choiceLabelFlow The Label decoration
 */
@Serializable
class MultiChoiceField(
    override val id: String = "",
    override val required: Boolean = true,
    override val readOnly: Boolean = false,
    override val top: Component? = null,
    override val bottom: Component? = null,
    override val start: Component? = null,
    override val end: Component? = null,
    @SerialName("itemId") private val itemId: String? = null,
    @SerialName("options") private val options: List<Component> = emptyList(),
    @SerialName("optionsLabel") private val optionsLabel: String = "",
    @SerialName("presence") private val presence: Presence = Presence.Visible,
    @SerialName("enabled") private val enabled: Boolean = true,
    val placeHolder: String = "",
    val searchable: Boolean = false,
    val choiceLabel: Component? = null,
    val orientation: Orientation = Orientation.Vertical
) : SelectionInputField(
//    id = id,
//    top = top,
//    bottom = bottom,
//    start = start,
//    end = end,
//    required = required,
//    readOnly = readOnly,
//    fieldPresence = presence,
//    options = options,
//    value = options.firstOrNull { it.id == itemId },
) {
    override val validation = ItemNotNullValidation()

    private val _choiceLabel = MutableStateFlow(choiceLabel)
    val choiceLabelFlow: StateFlow<Component?>
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