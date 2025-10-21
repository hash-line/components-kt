import kotlinx.serialization.Serializable

interface UserInterfaceEvent : Event

@Serializable
data class ChangeLanguageRequest(
    val language: String
) : UserInterfaceEvent


@Serializable
data class ChangeThemeRequest(
    val darkTheme: Boolean
) : UserInterfaceEvent

@Serializable
data object ToggleThemeRequest : UserInterfaceEvent

@Serializable
class Clicked(
    val component: Component
) : UserInterfaceEvent

@Serializable
class LinkClicked(
    val link: Link
) : UserInterfaceEvent