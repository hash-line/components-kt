import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

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
    val presenceFlow: StateFlow<Presence>

    val enabledFlow: StateFlow<Boolean>

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

@Serializable
abstract class BaseComponent() : Component {

//    constructor(
//        enabled: Boolean = true,
//        presence: Presence = Presence.Visible,
//        mapper: (suspend (Component, Event) -> Event)? = null
//    ) : this() {
//        _enabled.value = enabled
//        _presence.value = presence
//        this.mapper = mapper
//    }

    @kotlinx.serialization.Transient
    protected val eventsFlow = MutableSharedFlow<Event>(replay = 0)
    protected val flow: Flow<Event> by lazy { eventsFlowBuilder() }

    @kotlinx.serialization.Transient
    private val _presence = MutableStateFlow(Presence.Visible)
    override val presenceFlow: StateFlow<Presence>
        get() = _presence

    @kotlinx.serialization.Transient
    private val _enabled = MutableStateFlow(true)
    override val enabledFlow: MutableStateFlow<Boolean>
        get() = _enabled

    @kotlinx.serialization.Transient
    var mapper: (suspend (Component, Event) -> Event)? = null

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
        if(enabledFlow.value){
            eventsFlow.emit(mapper?.invoke(this, event) ?: event)
        }
    }
 }