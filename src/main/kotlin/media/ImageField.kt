package media

import Component
import ValidationCode

class ImageField(
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
    validation = ImageFileValidation(),
    presentationMode = presentationMode
)

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
