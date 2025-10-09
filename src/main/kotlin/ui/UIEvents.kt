interface UserInterfaceEvent : Event

data class ChangeLanguageRequest(
    val language: String
) : UserInterfaceEvent

data class ChangeThemeRequest(
    val darkTheme: Boolean
) : UserInterfaceEvent

data object ToggleThemeRequest : UserInterfaceEvent

class Clicked(
    val component: Component
) : UserInterfaceEvent

class LinkClicked(
    val link: Link
) : UserInterfaceEvent