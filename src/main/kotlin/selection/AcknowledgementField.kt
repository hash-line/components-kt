package selection

import BaseComponent
import Component
import Text
import Presence
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AcknowledgementItem(
    override val id: String = "",
    val acknowledged: Boolean = false
) : BaseComponent()

@Serializable
class AcknowledgementField(
    override val id: String = "",
    override val required: Boolean = true,
    override val readOnly: Boolean = false,
    override val top: Component? = null,
    override val bottom: Component? = null,
    override val start: Component? = null,
    override val end: Component? = null,
    @SerialName("acknowledged") private val acknowledged:  Boolean = false,
    @SerialName("presence") private val presence: Presence = Presence.Visible,
    @SerialName("enabled") private val enabled: Boolean = true,
    val label: Component? = null,
) : SelectionInputField(
    validation = ItemNotNullValidation(),
//    options = listOf(
//        AcknowledgementItem(id = id, acknowledged = acknowledged)
//    )
)