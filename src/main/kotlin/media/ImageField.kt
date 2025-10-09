package media

import Component
import Presence
import ValidationCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class ImageField(
    override val id: String = "",
    override val required: Boolean = true,
    override val readOnly: Boolean = false,
    override val top: Component? = null,
    override val bottom: Component? = null,
    override val start: Component? = null,
    override val end: Component? = null,
    @SerialName("value") private val value: String = "",
    @SerialName("presence") private val presence: Presence = Presence.Visible,
    @SerialName("enabled") private val enabled: Boolean = true,
    val placeHolder: Component? = null,
    override val presentationMode: PresentationMode = PresentationMode.Field,
) : MediaField(){

    override val validation = ImageFileValidation()

    init {
        setValueSilently(value)
        setPresence(presence)
        setEnabled(enabled)
    }
}

@Serializable
class ImageFileValidation : FileInputValidation {
    override fun validate(input: String?): Int {
        return when {
            input == null -> ValidationCode.UNDEFINED
            /*!input.toString().endsWith(".jpg", true) &&
                    !input.toString().endsWith(".jpeg", true) &&
                    !input.toString().endsWith(".png", true) &&
                    !input.toString().endsWith(".gif", true) -> ValidationCode.INVALID_FORMAT*/

            else -> ValidationCode.VALID
        }
    }
}
