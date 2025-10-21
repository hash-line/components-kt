package media

import Component
import Presence
import ValidationCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class VideoField(
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
) : MediaField(
){
    override val validation = VideoFileValidation()

    init {
        setValueSilently(value)
        setPresence(presence)
        setEnabled(enabled)
    }
}

@Serializable
class VideoFileValidation : FileInputValidation {
    override fun validate(input: String?): Int {
        return when {
            input == null -> ValidationCode.UNDEFINED
            /*!input.toString().endsWith(".mp4", true) &&
                    !input.toString().endsWith(".avi", true) &&
                    !input.toString().endsWith(".mov", true) &&
                    !input.toString().endsWith(".mkv", true) -> ValidationCode.INVALID_FORMAT*/

            else -> ValidationCode.VALID
        }
    }
}
