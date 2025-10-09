package text

import Component
import Presence
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class NumberField(
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
    val description: String = "",
    val controlsEnabled: Boolean = false
) : TextInputField(
){
    override val validation = TextNotEmptyValidation()
}