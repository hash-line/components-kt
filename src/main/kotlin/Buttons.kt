interface Button : Component {
    suspend fun click()

}

abstract class ButtonEvent : Event

class ClickEvent(
    val button: Button
) : ButtonEvent()

abstract class BaseButton(
    id: String = "",
    presence: Presence = Presence.Visible,
    enabled: Boolean = true
) : Button, BaseComponent(
    id = id,
    presence = presence,
    enabled = enabled
) {
    override suspend fun click() {
        raiseEvent(
            ClickEvent(
                button = this
            )
        )
    }
}

class TextButton(
    id: String,
    enabled: Boolean,
    presence: Presence,
    val text: Text,
) : BaseButton(
    id = id,
    enabled = enabled,
    presence = presence
) {
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

class IconButton(
    id: String = "",
    enabled: Boolean = true,
    presence: Presence = Presence.Visible,
    val icon: Icon
) : BaseButton(
    id = id,
    enabled = enabled,
    presence = presence
)

class TextIconButton(
    id: String = "",
    enabled: Boolean = true,
    presence: Presence = Presence.Visible,
    val icon: Icon,
    val text: Text
) : BaseButton(
    id = id,
    enabled = enabled,
    presence = presence
)