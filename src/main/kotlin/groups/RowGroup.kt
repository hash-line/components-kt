package groups

import Component
import Presence

class RowGroup(
    id: String = "",
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null,
    divider: Component? = null,
    children: List<Component> = emptyList(),
    enabled: Boolean = true,
    presence: Presence = Presence.Visible
) : BaseComponentGroup(
    id = id,
    top = top,
    bottom = bottom,
    start = start,
    end = end,
    divider = divider,
    initialChildren = children,
    enabled = enabled,
    presence = presence
)