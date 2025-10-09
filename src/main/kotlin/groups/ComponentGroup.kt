package groups

import EmptyEvent
import Event
import Component
import Presence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.merge

interface ComponentGroup : LabeledComponent {

    val count: Int

    /**
     * A list of components that are part of this group.
     *
     * This property allows you to access all components within the group.
     */
    val children: StateFlow<List<Component>>

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

abstract class BaseComponentGroup(
    id: String = "",
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null,
    initialChildren: List<Component> = emptyList(),
    enabled: Boolean = true,
    presence: Presence = Presence.Visible,
    open val divider: Component? = null,
) : BaseLabeledComponent(
    id = id,
    enabled = enabled,
    presence = presence,
    top = top,
    bottom = bottom,
    start = start,
    end = end
), ComponentGroup {

    private val _children: MutableStateFlow<List<Component>> = MutableStateFlow(initialChildren)
    override val children: StateFlow<List<Component>> = _children

    override val count: Int
        get() = _children.value.size

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _childrenEvents = children.flatMapLatest { fields ->
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

    override fun add(component: Component) {
        _children.value = _children.value + component
    }

    override fun remove(component: Component) {
        _children.value = _children.value - component
    }
}