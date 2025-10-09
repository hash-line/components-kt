package groups

import Component
import Presence
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class RowGroup(
    override val id: String = "",
    override val top: Component? = null,
    override val bottom: Component? = null,
    override val start: Component? = null,
    override val end: Component? = null,
    override val divider: Component? = null,
    @SerialName("value") private val value: String = "",
    @SerialName("children") private val children: List<Component> = emptyList(),
    @SerialName("presence") private val presence: Presence = Presence.Visible,
    @SerialName("enabled") private val enabled: Boolean = true
) : BaseComponentGroup(){
    init {
        setChildren(children)
    }
}