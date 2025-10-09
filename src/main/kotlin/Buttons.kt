import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface Button : Component {
    suspend fun click()
}

@Serializable
abstract class ButtonEvent : Event

@Serializable
class ClickEvent(
    val button: Button
) : ButtonEvent()

@Serializable
abstract class BaseButton() : Button, BaseComponent() {

    override suspend fun click() {
        raiseEvent(
            ClickEvent(
                button = this
            )
        )
    }
}

@Serializable
class TextButton(
    override val id: String,
    @SerialName("presence") private val presence: Presence = Presence.Visible,
    @SerialName("enabled") private val enabled: Boolean = true,
    val text: Text,
) : BaseButton() {
    constructor(
        text: String,
        id: String = "",
        enabled: Boolean = true,
        presence: Presence = Presence.Visible
    ) : this(
        text = PlainText(text = text),
        id = id,
        enabled = enabled,
        presence = presence
    )
}


@Serializable
class IconButton(
    override val id: String = "",
    @SerialName("presence") private val presence: Presence = Presence.Visible,
    @SerialName("enabled") private val enabled: Boolean = true,
    val icon: Icon
) : BaseButton() {
    init {
        setPresence(presence)
        setEnabled(enabled)
    }
}


@Serializable
class TextIconButton(
    override val id: String = "",
    @SerialName("presence") private val presence: Presence = Presence.Visible,
    @SerialName("enabled") private val enabled: Boolean = true,
    val icon: Icon,
    val text: Text
) : BaseButton() {
    init {
        setPresence(presence)
        setEnabled(enabled)
    }
}