package text

import Component
import Presence

class NumberField(
    id: String = "",
    value: String = "",
    required: Boolean = true,
    readOnly: Boolean = false,
    presence: Presence = Presence.Visible,
    singleLine: Boolean = true,
    placeHolder: String = "",
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null,
    val description: String = "",
    val controlsEnabled: Boolean = false
) : TextInputField(
    id = id,
    value = value,
    top = top,
    bottom = bottom,
    start = start,
    end = end,
    required = required,
    readOnly = readOnly,
    presence = presence,
    singleLine = singleLine,
    placeHolder = placeHolder,
    validation = TextNotEmptyValidation()
)