import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Anything that can be displayed on the screen is a [Component]. A [Component] is the base building block of the UI.
 * Each [Component] can emit [Event]s and has a unique [id] to identify it within the view hierarchy.
 *
 * @see Event
 */
interface Component : Flow<Event> {

    /**
     * A unique identifier for the component.
     *
     * This ID is used to identify the Component within the view hierarchy.
     */
    val id: String

    /**
     * A [StateFlow] that reflects the current [Presence] of this component.
     *
     * This flow controls whether the component is currently visible, hidden, or forgotten.
     */
    val presence: StateFlow<Presence>

    val enabled: StateFlow<Boolean>

    /**
     * Updates the presence of the component to the given [Presence].
     *
     * This method allows you to dynamically change the role of the field in the form, such as hiding it, making it visible,
     * or removing its influence on the form entirely.
     *
     * @param newPresence The new presence state to apply to the field.
     */
    fun setPresence(newPresence: Presence)

    fun setEnabled(enabled: Boolean)
}

abstract class BaseComponent(
    override val id: String = "",
    enabled: Boolean = true,
    presence: Presence = Presence.Visible,
    replay: Int = 0,
    private val mapper: (suspend (Component, Event) -> Event)? = null
) : Component {

    protected val eventsFlow = MutableSharedFlow<Event>(replay = replay)
    protected val flow: Flow<Event> by lazy { eventsFlowBuilder() }

    private val _presence = MutableStateFlow(presence)
    override val presence: StateFlow<Presence>
        get() = _presence

    private val _enabled = MutableStateFlow(enabled)
    override val enabled: MutableStateFlow<Boolean>
        get() = _enabled

    open fun eventsFlowBuilder(): Flow<Event> = eventsFlow

    override fun setPresence(newPresence: Presence) {
        _presence.value = newPresence
    }

    override fun setEnabled(enabled: Boolean) {
        _enabled.value = enabled
    }

    override suspend fun collect(collector: FlowCollector<Event>) {
        println("${id}.collect: flow = $flow")
        flow.collect(collector)
     }

    open suspend fun raiseEvent(event: Event) {
        if(enabled.value){
            eventsFlow.emit(mapper?.invoke(this, event) ?: event)
        }
    }
 }