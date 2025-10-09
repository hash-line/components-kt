package groups

import Event
import BaseComponent
import Component
import Presence
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

interface LabeledComponent : Component {

    /**
     * The top component of the labeled component.
     */
    val top: Component?

    /**
     * The bottom component of the labeled component.
     */
    val bottom: Component?

    /**
     * The start component of the labeled component.
     */
    val start: Component?

    /**
     * The end component of the labeled component.
     */
    val end: Component?
}

open class BaseLabeledComponent(
    id: String = "",
    enabled: Boolean = true,
    presence: Presence = Presence.Visible,
    open val top: Component? = null,
    open val bottom: Component? = null,
    open val start: Component? = null,
    open val end: Component? = null
): BaseComponent(
    id = id,
    enabled = enabled,
    presence = presence
){

    override fun eventsFlowBuilder(): Flow<Event> {
        val flows = listOfNotNull(
            top as? Flow<Event>,
            bottom as? Flow<Event>,
            start as? Flow<Event>,
            end as? Flow<Event>
        )
        return if (flows.isEmpty()) {
            super.eventsFlowBuilder()
        } else {
            merge(*flows.toTypedArray(), super.eventsFlowBuilder())
        }
    }
}