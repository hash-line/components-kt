package selection

import BaseComponent
import Component
import Text
import Presence

class AcknowledgementItem(
    id: String = "",
    val acknowledged: Boolean = false
) : BaseComponent(id = id)

class AcknowledgementField(
    id: String = "",
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null,
    required: Boolean = true,
    acknowledged: Boolean = false,
    fieldPresence: Presence = Presence.Visible,
    val text: Text,
) : SelectionInputField(
    id = id,
    value = null,
    top = top,
    bottom = bottom,
    start = start,
    end = end,
    required = required,
    fieldPresence = fieldPresence,
    validation = ItemNotNullValidation(),
    options = listOf(
        AcknowledgementItem(id = id, acknowledged = acknowledged)
    )
)