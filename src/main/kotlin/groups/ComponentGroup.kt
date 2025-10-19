package groups

import Component
import EmptyEvent
import Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.merge
import kotlinx.serialization.Serializable

interface ComponentGroup : LabeledComponent {

    val count: Int

    /**
     * A list of components that are part of this group.
     *
     * This property allows you to access all components within the group.
     */
    val childrenFlow: StateFlow<List<Component>>

    /**
     * Adds a component to the group.
     *
     * @param component The component to be added to the group.
     */
    fun add(component: Component)

    /**
     * Removes a component from the group.
     *
     * @param component The component to be removed from the group.
     */
    fun remove(component: Component)
}

@Serializable
abstract class BaseComponentGroup() : BaseLabeledComponent(), ComponentGroup {

    abstract val divider: Component?

    @kotlinx.serialization.Transient
    private val _children: MutableStateFlow<List<Component>> = MutableStateFlow(emptyList())
    @kotlinx.serialization.Transient
    override val childrenFlow: StateFlow<List<Component>> = _children

    override val count: Int
        get() = _children.value.size

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _childrenEvents = childrenFlow.flatMapLatest { fields ->
        fields.asFlow()
            .flatMapMerge { component ->
                component as Flow<Event>
            }
    }

    override fun eventsFlowBuilder(): Flow<Event> = merge(
        super.eventsFlowBuilder(),
        _childrenEvents.filterNot {
            it is EmptyEvent
        }
    )

    fun setChildren(components: List<Component>) {
        _children.value = components
    }

    override fun add(component: Component) {
        _children.value += component
    }

    override fun remove(component: Component) {
        _children.value -= component
    }
}