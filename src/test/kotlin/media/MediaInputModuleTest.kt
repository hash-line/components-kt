package media

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class MediaInputModuleTest {

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        serializersModule = MediaInputModule
    }

    @Test
    fun `test polymorphic serialization of ImageField`() {
        // Given
        val imageField = ImageField(
            id = "image-test",
            value = "image.jpg",
            presentationMode = PresentationMode.Frame
        )

        // When
        val jsonString = json.encodeToString<MediaField>(imageField)

        // Then
        assertNotNull(jsonString)
        assertTrue(jsonString.contains("\"type\": \"media.ImageField\""))
        assertTrue(jsonString.contains("\"id\": \"image-test\""))
        assertTrue(jsonString.contains("\"value\": \"image.jpg\""))
        assertTrue(jsonString.contains("\"presentationMode\": \"Frame\""))
    }

    @Test
    fun `test polymorphic deserialization of ImageField`() {
        // Given
        val jsonString = """
        {
            "type": "media.ImageField",
            "id": "image-test",
            "required": true,
            "readOnly": false,
            "value": "test-image.png",
            "presentationMode": "Field",
            "presence": "Visible",
            "enabled": true
        }
        """.trimIndent()

        // When
        val imageField = json.decodeFromString<MediaField>(jsonString)

        // Then
        assertNotNull(imageField)
        assertTrue(imageField is ImageField)
        assertEquals("image-test", imageField.id)
        assertEquals("test-image.png", imageField.valueFlow.value)
        assertEquals(PresentationMode.Field, imageField.presentationMode)
    }

    @Test
    fun `test polymorphic serialization of VideoField`() {
        // Given
        val videoField = VideoField(
            id = "video-test",
            value = "video.mp4",
            presentationMode = PresentationMode.Frame
        )

        // When
        val jsonString = json.encodeToString<MediaField>(videoField)

        // Then
        assertNotNull(jsonString)
        assertTrue(jsonString.contains("\"type\": \"media.VideoField\""))
        assertTrue(jsonString.contains("\"id\": \"video-test\""))
        assertTrue(jsonString.contains("\"value\": \"video.mp4\""))
        assertTrue(jsonString.contains("\"presentationMode\": \"Frame\""))
    }

    @Test
    fun `test polymorphic deserialization of VideoField`() {
        // Given
        val jsonString = """
        {
            "type": "media.VideoField",
            "id": "video-test",
            "required": true,
            "readOnly": false,
            "value": "test-video.mov",
            "presentationMode": "Frame",
            "presence": "Visible",
            "enabled": true
        }
        """.trimIndent()

        // When
        val videoField = json.decodeFromString<MediaField>(jsonString)

        // Then
        assertNotNull(videoField)
        assertTrue(videoField is VideoField)
        assertEquals("video-test", videoField.id)
        assertEquals("test-video.mov", videoField.valueFlow.value)
        assertEquals(PresentationMode.Frame, videoField.presentationMode)
    }

    @Test
    fun `test polymorphic serialization of DocumentField`() {
        // Given
        val documentField = DocumentField(
            id = "document-test",
            value = "document.pdf",
            presentationMode = PresentationMode.Field
        )

        // When
        val jsonString = json.encodeToString<MediaField>(documentField)

        // Then
        assertNotNull(jsonString)
        assertTrue(jsonString.contains("\"type\": \"media.DocumentField\""))
        assertTrue(jsonString.contains("\"id\": \"document-test\""))
        assertTrue(jsonString.contains("\"value\": \"document.pdf\""))
        assertTrue(jsonString.contains("\"presentationMode\": \"Field\""))
    }

    @Test
    fun `test polymorphic deserialization of DocumentField`() {
        // Given
        val jsonString = """
        {
            "type": "media.DocumentField",
            "id": "document-test",
            "required": true,
            "readOnly": false,
            "value": "test-document.docx",
            "presentationMode": "Field",
            "presence": "Visible",
            "enabled": true
        }
        """.trimIndent()

        // When
        val documentField = json.decodeFromString<MediaField>(jsonString)

        // Then
        assertNotNull(documentField)
        assertTrue(documentField is DocumentField)
        assertEquals("document-test", documentField.id)
        assertEquals("test-document.docx", documentField.valueFlow.value)
        assertEquals(PresentationMode.Field, documentField.presentationMode)
    }

    @Test
    fun `test polymorphic serialization round trip`() {
        // Given
        val originalField = ImageField(
            id = "round-trip-test",
            value = "round-trip-image.jpg",
            presentationMode = PresentationMode.Frame
        )

        // When
        val jsonString = json.encodeToString<MediaField>(originalField)
        val deserializedField = json.decodeFromString<MediaField>(jsonString)

        // Then
        assertNotNull(deserializedField)
        assertTrue(deserializedField is ImageField)
        assertEquals(originalField.id, deserializedField.id)
        assertEquals(originalField.valueFlow.value, deserializedField.valueFlow.value)
        assertEquals(originalField.presentationMode, deserializedField.presentationMode)
    }

    @Test
    fun `test polymorphic serialization with mixed field types`() {
        // Given
        val fields = listOf(
            ImageField(id = "image-1", value = "image1.jpg"),
            VideoField(id = "video-1", value = "video1.mp4"),
            DocumentField(id = "document-1", value = "document1.pdf")
        )

        // When
        val jsonString = json.encodeToString<List<MediaField>>(fields)

        // Then
        assertNotNull(jsonString)
        assertTrue(jsonString.contains("\"type\": \"media.ImageField\""))
        assertTrue(jsonString.contains("\"type\": \"media.VideoField\""))
        assertTrue(jsonString.contains("\"type\": \"media.DocumentField\""))
    }

    @Test
    fun `test polymorphic deserialization with mixed field types`() {
        // Given
        val jsonString = """
        [
            {
                "type": "media.ImageField",
                "id": "image-1",
                "value": "test-image.jpg",
                "presentationMode": "Field",
                "required": true,
                "readOnly": false,
                "presence": "Visible",
                "enabled": true
            },
            {
                "type": "media.DocumentField",
                "id": "document-1",
                "value": "test-document.pdf",
                "presentationMode": "Field",
                "required": true,
                "readOnly": false,
                "presence": "Visible",
                "enabled": true
            }
        ]
        """.trimIndent()

        // When
        val fields = json.decodeFromString<List<MediaField>>(jsonString)

        // Then
        assertNotNull(fields)
        assertEquals(2, fields.size)
        assertTrue(fields[0] is ImageField)
        assertTrue(fields[1] is DocumentField)
        assertEquals("image-1", fields[0].id)
        assertEquals("document-1", fields[1].id)
    }

    @Test
    fun `test validation classes serialization`() {
        // Given
        val imageValidation = ImageFileValidation()
        val videoValidation = VideoFileValidation()
        val documentValidation = DocumentFileValidation()

        // When
        val imageJson = json.encodeToString<FileInputValidation>(imageValidation)
        val videoJson = json.encodeToString<FileInputValidation>(videoValidation)
        val documentJson = json.encodeToString<FileInputValidation>(documentValidation)

        // Then
        assertNotNull(imageJson)
        assertNotNull(videoJson)
        assertNotNull(documentJson)
        assertTrue(imageJson.contains("\"type\": \"media.ImageFileValidation\""))
        assertTrue(videoJson.contains("\"type\": \"media.VideoFileValidation\""))
        assertTrue(documentJson.contains("\"type\": \"media.DocumentFileValidation\""))
    }
}
