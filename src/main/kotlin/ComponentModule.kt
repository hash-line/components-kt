import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import media.MediaInputModule
import selection.SelectionInputModule
import text.TextInputModule

val json = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
    encodeDefaults = true
    componentModule
}

val componentModule = SerializersModule {
    MediaInputModule
    SelectionInputModule
    TextInputModule
}