package groups

import Event
import BaseComponent
import Component
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

abstract class BaseLabeledComponent(): BaseComponent(){

    abstract val top: Component?

    abstract val bottom: Component?

    abstract val start: Component?

    abstract val end: Component?

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