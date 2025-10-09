package media

import Component
import ValidationCode

class VideoField(
    id: String,
    value: String? = null,
    enabled: Boolean = true,
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null,
    required: Boolean = true,
    readOnly: Boolean = false,
    placeHolder: String = "",
    presentationMode: PresentationMode = PresentationMode.Field
) : MediaField(
    id = id,
    value = value,
    enabled = enabled,
    top = top,
    bottom = bottom,
    start = start,
    end = end,
    required = required,
    readOnly = readOnly,
    placeHolder = placeHolder,
    validation = VideoFileValidation(),
    presentationMode = presentationMode
)

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
