
class Container(
    id: String = "",
    enabled: Boolean = true,
    presence: Presence = Presence.Visible,
    //val style: ContainerStyle = ContainerStyle.defaultStyle,
    val body: Component,
    val onClick: (suspend Container.(Clicked) -> Event)? = null
) : BaseComponent(
    id = id,
    enabled = enabled,
    presence = presence,
    mapper = { item, event ->
        when(event){
            is Clicked -> (item as Container).onClick?.invoke(item, event) ?: event
            else -> event
        }
    }
)

class ComponentWrapper(
    id: String = "",
    enabled: Boolean = true,
    presence: Presence = Presence.Visible,
    val body: Component,
    val top: Component? = null,
    val bottom: Component? = null,
    val start: Component? = null,
    val end: Component? = null,
) :  BaseComponent(
    id = id,
    enabled = enabled,
    presence = presence
)



///**
// * `ContainerStyle` is a sealed class representing different styles for displaying containers
// * This class provides a flexible way to customize the appearance of containers in Zindigi Application.
// *
// * @property enabledContainerColor The background color of the digit container.
// * @property focusedBorderColor The border color when the digit container is focused.
// * @property unfocusedBorderColor The border color when the digit container is not focused.
// * @property focusedBorderWidth The border width when the digit container is focused.
// * @property unfocusedBorderWidth The border width when the digit container is not focused.
// * @property errorColor The color used to indicate an error state in the digit container.
// */
//sealed class ContainerStyle(
//    open val enabledContainerColor: Color = Color.Unspecified,
//    open val disabledContainerColor: Color = Color.Unspecified,
//    open val focusedBorderColor: Color = Color.Unspecified,
//    open val unfocusedBorderColor: Color = Color.Unspecified,
//    open val enabledBorderColor: Color = Color.Unspecified,
//    open val disabledBorderColor: Color = Color.Unspecified,
//    open val enabledContentColor: Color = Color.Unspecified,
//    open val disabledContentColor: Color = Color.Unspecified,
//    open val focusedBorderWidth: Dp = 2.dp,
//    open val unfocusedBorderWidth: Dp = 2.dp,
//    open val errorColor: Color = Color.Unspecified,
//    open val shape: Shape = RoundedCornerShape(8.dp),
//    open val shadow: Shadow = Shadow(elevation = 1.dp, color = Color.Black)
//) {
//
//    /**
//     * Returns the appropriate border width based on the focus state of the digit container.
//     *
//     * @param focused Whether the digit container is currently focused.
//     * @return The border width to be used for the digit container.
//     */
//    fun borderWidth(focused: Boolean): Dp {
//        return if (focused) focusedBorderWidth else unfocusedBorderWidth
//    }
//
//    /**
//     * Returns the appropriate border color based on the focus and error states of the digit container.
//     *
//     * @param focused Whether the digit container is currently focused.
//     * @param error Whether the digit container is in an error state.
//     * @return The border color to be used for the digit container.
//     */
//    fun borderColor(focused: Boolean, error: Boolean): Color {
//        return when {
//            error -> errorColor
//            focused -> focusedBorderColor
//            else -> unfocusedBorderColor
//        }
//    }
//
//    /**
//     * Represents an outlined style for the digit container of our [ZindigiOTPInputField].
//     *
//     * @property size The size of the digit container.
//     * @property shape The shape of the digit container.
//     */
//    data class Outlined(
//        override val shadow: Shadow = Shadow(elevation = 1.dp, color = Color.Black),
//        override val shape: Shape = RoundedCornerShape(12.dp),
//        override val enabledContainerColor: Color = Color.Unspecified,
//        override val focusedBorderColor: Color = Color.Unspecified,
//        override val unfocusedBorderColor: Color = Color.Unspecified,
//        override val enabledBorderColor: Color = Color.Unspecified,
//        override val disabledBorderColor: Color = Color.Unspecified,
//        override val enabledContentColor: Color = Color.Unspecified,
//        override val disabledContentColor: Color = Color.Unspecified,
//        override val focusedBorderWidth: Dp = 1.0.dp,
//        override val unfocusedBorderWidth: Dp = 0.8.dp,
//        override val errorColor: Color = Color.Unspecified,
//    ) : ContainerStyle(
//        enabledContainerColor = enabledContainerColor,
//        focusedBorderColor = focusedBorderColor,
//        unfocusedBorderColor = unfocusedBorderColor,
//        unfocusedBorderWidth = unfocusedBorderWidth,
//        focusedBorderWidth = focusedBorderWidth,
//        errorColor = errorColor
//    )
//
//
//    data class Filled(
//        override val shadow: Shadow = Shadow(elevation = 1.dp, color = Color.Black),
//        override val shape: Shape = RoundedCornerShape(12.dp),
//        override val enabledContainerColor: Color = Color.Unspecified,
//        override val disabledContainerColor: Color = Color.Unspecified,
//        override val focusedBorderColor: Color = Color.Unspecified,
//        override val unfocusedBorderColor: Color = Color.Unspecified,
//        override val enabledBorderColor: Color = Color.Unspecified,
//        override val disabledBorderColor: Color = Color.Unspecified,
//        override val enabledContentColor: Color = Color.Unspecified,
//        override val disabledContentColor: Color = Color.Unspecified,
//        override val focusedBorderWidth: Dp = 1.0.dp,
//        override val unfocusedBorderWidth: Dp = 0.8.dp,
//        override val errorColor: Color = Color.Unspecified,
//    ) : ContainerStyle(
//        enabledContainerColor = enabledContainerColor,
//        focusedBorderColor = focusedBorderColor,
//        unfocusedBorderColor = unfocusedBorderColor,
//        unfocusedBorderWidth = unfocusedBorderWidth,
//        focusedBorderWidth = focusedBorderWidth,
//        errorColor = errorColor
//    )
//
//    companion object {
//        val defaultStyle: ContainerStyle = Outlined()
//    }
//}