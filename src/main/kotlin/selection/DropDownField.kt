package selection

import Component
import InputValidation
import Presence
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [DropDownField] is a [selection.SelectionInputField] where user can select one option from a list. In [DropDownField] field all available options are not
 * visible, when user interacts with the field only than the available options are shown. e.g. dropdown field for selecting purpose of payment.
 *
 * @property placeHolder The text to display when no option is selected.
 * @property optionsLabelFlow The title to display above the dropdown.
 *
 * @see selection.SelectionInputField
 * @see InputValidation
 */
@Serializable
class DropDownField(
    override val id: String = "",
    override val required: Boolean = true,
    override val readOnly: Boolean = false,
    override val top: Component? = null,
    override val bottom: Component? = null,
    override val start: Component? = null,
    override val end: Component? = null,
    @SerialName("itemId") private val itemId:  String? = null,
    @SerialName("options") private val options: List<Component> = emptyList(),
    @SerialName("optionsLabel") private val optionsLabel: String = "",
    @SerialName("presence") private val presence: Presence = Presence.Visible,
    @SerialName("enabled") private val enabled: Boolean = true,
    val placeHolder: String = "",
    val searchable: Boolean = false
) : SelectionInputField(
    //options = options,
    //value = options.firstOrNull { it.id == itemId },
) {
    override val validation: InputValidation<Component?> = ItemNotNullValidation()

    init {

//        setOptions(
//            optionsLabel = optionsLabel,
//            options = options,
//            selectedIndex = options.indexOfFirst { it.id == itemId }
//        )
    }

    private val _optionsLabel = MutableStateFlow(optionsLabel)
    val optionsLabelFlow: StateFlow<String>
        get() = _optionsLabel

    suspend fun setOptions(optionsLabel: String, options: List<Component>, selectedIndex: Int = -1) {
        setOptions(options, selectedIndex)
        _optionsLabel.value = optionsLabel
    }

}