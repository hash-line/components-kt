/**
 * [Presence] represents the presence of a Component.
 *
 * This enum controls whether a Component is displayed, hidden, or entirely ignored.
 *
 */
enum class Presence {
    /**
     * A component that should not be visible to the user but its presence is considered in the UI.
     */
    Hidden,

    /**
     * A component that is both displayed and its presence is evaluated in UI.
     */
    Visible,

    /**
     * A component that is neither visible nor is its presense considered in UI.
     */
    Forgotten
}