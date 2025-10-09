package media

import BaseInputField
import Component
import InputValidation
import Presence

enum class PresentationMode {
    Frame,
    Field
}

/**
 * [MediaField] is an input field that can be used to upload media files.
 *
 * @property link The link to the media files. This can be an uploaded files link, hence this is different from the File of the field.
 */
abstract class MediaField(
    id: String = "",
    value: String? = null,
    enabled: Boolean = true,
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null,
    required: Boolean = true,
    readOnly: Boolean = false,
    fieldPresence: Presence = Presence.Visible,
    validation: FileInputValidation,
    open val link: String = "",
    open val placeHolder: String = "",
    open val presentationMode: PresentationMode = PresentationMode.Field
) : BaseInputField<String?>(
    id = id,
    value = value,
    presence = fieldPresence,
    enabled = enabled,
    top = top,
    bottom = bottom,
    start = start,
    end = end,
    required = required,
    readOnly = readOnly,
    validation = validation
) {

    override suspend fun clear() {
        setValue(null)
    }

}

/**
 * [FileInputValidation] represents the validation rules for a files input field.
 */
interface FileInputValidation : InputValidation<String?>




