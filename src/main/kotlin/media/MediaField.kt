package media

import BaseInputField
import InputValidation
import kotlinx.serialization.Serializable

enum class PresentationMode {
    Frame,
    Field
}

/**
 * [MediaField] is an input field that can be used to upload media files.
 *
 */

@Serializable
abstract class MediaField(
    override val validation: FileInputValidation
) : BaseInputField<String?>() {

    abstract val presentationMode: PresentationMode

    override val default: String?
        get() = null

    override suspend fun clear() {
        setValue(null)
    }

}

/**
 * [FileInputValidation] represents the validation rules for a files input field.
 */
interface FileInputValidation : InputValidation<String?>




