import kotlinx.serialization.Serializable

interface Drawable

@Serializable
abstract class Icon : Drawable

//class ResIcon(
//    val resId: DrawableResource
//) : Icon(
//
//)
//
//class VectorIcon(
//    val icon: ImageVector
//) : Icon()

@Serializable
class NetworkIcon(
    val url: String
) : Icon()

@Serializable
abstract class Image : Drawable

@Serializable
data class ResImage(val resId: Int) : Image()

@Serializable
data class NetworkImage(val url: String) : Image()

//fun ImageVector.asIcon(color:.Unspecified): VectorIcon =
//    VectorIcon(color = color, icon = this)
//
//fun DrawableResource.asIcon(color:.Unspecified): ResIcon =
//    ResIcon(color = color, resId = this)

