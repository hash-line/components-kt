package media

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertNotNull

class MediaInputModuleDebugTest {

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        serializersModule = MediaInputModule
    }

    @Test
    fun `debug polymorphic ImageField serialization`() {
        // Given
        val imageField = ImageField(
            id = "test-image",
            value = "test.jpg"
        )

        // When
        val jsonString = json.encodeToString<MediaField>(imageField)

        // Then
        assertNotNull(jsonString)
        println("Polymorphic serialized ImageField: $jsonString")
    }
}
