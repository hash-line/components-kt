package text

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TextInputModuleTest {

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        serializersModule = TextInputModule
    }

    @Test
    fun `test polymorphic serialization of TextField`() {
        // Given
        val textField = TextField(
            id = "test-field",
            value = "Hello World",
            minLength = 5,
            maxLength = 100
        )

        // When
        val jsonString = json.encodeToString<TextInputField>(textField)

        // Then
        assertNotNull(jsonString)
        assertTrue(jsonString.contains("\"type\": \"text.TextField\""))
        assertTrue(jsonString.contains("\"id\": \"test-field\""))
        assertTrue(jsonString.contains("\"value\": \"Hello World\""))
    }

    @Test
    fun `test polymorphic deserialization of TextField`() {
        // Given
        val jsonString = """
        {
            "type": "text.TextField",
            "id": "test-field",
            "required": true,
            "readOnly": false,
            "singleLine": true,
            "placeHolder": "Enter text",
            "label": "Text Field",
            "value": "Test Value",
            "minLength": 3,
            "maxLength": 50,
            "presence": "Visible",
            "enabled": true
        }
        """.trimIndent()

        // When
        val textField = json.decodeFromString<TextInputField>(jsonString)

        // Then
        assertNotNull(textField)
        assertTrue(textField is TextField)
        assertEquals("test-field", textField.id)
        assertEquals("Test Value", textField.valueFlow.value)
    }

    @Test
    fun `test polymorphic serialization of EmailField`() {
        // Given
        val emailField = EmailField(
            id = "email-field",
            value = "test@example.com"
        )

        // When
        val jsonString = json.encodeToString<TextInputField>(emailField)

        // Then
        assertNotNull(jsonString)
        assertTrue(jsonString.contains("\"type\": \"text.EmailField\""))
        assertTrue(jsonString.contains("\"id\": \"email-field\""))
        assertTrue(jsonString.contains("\"value\": \"test@example.com\""))
    }

    @Test
    fun `test polymorphic deserialization of EmailField`() {
        // Given
        val jsonString = """
        {
            "type": "text.EmailField",
            "id": "email-field",
            "required": true,
            "readOnly": false,
            "singleLine": true,
            "placeHolder": "Enter email",
            "label": "Email",
            "value": "user@example.com",
            "presence": "Visible",
            "enabled": true
        }
        """.trimIndent()

        // When
        val emailField = json.decodeFromString<TextInputField>(jsonString)

        // Then
        assertNotNull(emailField)
        assertTrue(emailField is EmailField)
        assertEquals("email-field", emailField.id)
        assertEquals("user@example.com", emailField.valueFlow.value)
    }

    @Test
    fun `test polymorphic serialization of NameField`() {
        // Given
        val nameField = NameField(
            id = "name-field",
            value = "John Doe",
            splitName = true
        )

        // When
        val jsonString = json.encodeToString<TextInputField>(nameField)

        // Then
        assertNotNull(jsonString)
        assertTrue(jsonString.contains("\"type\": \"text.NameField\""))
        assertTrue(jsonString.contains("\"id\": \"name-field\""))
        assertTrue(jsonString.contains("\"value\": \"John Doe\""))
        assertTrue(jsonString.contains("\"splitName\": true"))
    }

    @Test
    fun `test polymorphic serialization of NumberField`() {
        // Given
        val numberField = NumberField(
            id = "number-field",
            value = "12345",
            description = "Enter a number",
            controlsEnabled = true
        )

        // When
        val jsonString = json.encodeToString<TextInputField>(numberField)

        // Then
        assertNotNull(jsonString)
        assertTrue(jsonString.contains("\"type\": \"text.NumberField\""))
        assertTrue(jsonString.contains("\"id\": \"number-field\""))
        assertTrue(jsonString.contains("\"value\": \"12345\""))
        assertTrue(jsonString.contains("\"description\": \"Enter a number\""))
        assertTrue(jsonString.contains("\"controlsEnabled\": true"))
    }

    @Test
    fun `test polymorphic serialization round trip`() {
        // Given
        val originalField = TextField(
            id = "round-trip-test",
            value = "Round Trip Test",
            minLength = 10,
            maxLength = 100
        )

        // When
        val jsonString = json.encodeToString<TextInputField>(originalField)
        val deserializedField = json.decodeFromString<TextInputField>(jsonString)

        // Then
        assertNotNull(deserializedField)
        assertTrue(deserializedField is TextField)
        assertEquals(originalField.id, deserializedField.id)
        assertEquals(originalField.valueFlow.value, deserializedField.valueFlow.value)
        assertEquals(originalField.minLength, (deserializedField as TextField).minLength)
        assertEquals(originalField.maxLength, deserializedField.maxLength)
    }

    @Test
    fun `test polymorphic serialization with mixed field types`() {
        // Given
        val fields = listOf(
            TextField(id = "text-1", value = "Text Field"),
            EmailField(id = "email-1", value = "email@test.com"),
            NameField(id = "name-1", value = "John Doe"),
            NumberField(id = "number-1", value = "12345")
        )

        // When
        val jsonString = json.encodeToString<List<TextInputField>>(fields)

        // Then
        assertNotNull(jsonString)
        assertTrue(jsonString.contains("\"type\": \"text.TextField\""))
        assertTrue(jsonString.contains("\"type\": \"text.EmailField\""))
        assertTrue(jsonString.contains("\"type\": \"text.NameField\""))
        assertTrue(jsonString.contains("\"type\": \"text.NumberField\""))
    }

    @Test
    fun `test polymorphic deserialization with mixed field types`() {
        // Given
        val jsonString = """
        [
            {
                "type": "text.TextField",
                "id": "text-1",
                "value": "Text Field",
                "required": true,
                "readOnly": false,
                "singleLine": true,
                "placeHolder": "",
                "label": "",
                "minLength": 0,
                "maxLength": 2147483647,
                "presence": "Visible",
                "enabled": true
            },
            {
                "type": "text.EmailField",
                "id": "email-1",
                "value": "email@test.com",
                "required": true,
                "readOnly": false,
                "singleLine": true,
                "placeHolder": "",
                "label": "",
                "presence": "Visible",
                "enabled": true
            }
        ]
        """.trimIndent()

        // When
        val fields = json.decodeFromString<List<TextInputField>>(jsonString)

        // Then
        assertNotNull(fields)
        assertEquals(2, fields.size)
        assertTrue(fields[0] is TextField)
        assertTrue(fields[1] is EmailField)
        assertEquals("text-1", fields[0].id)
        assertEquals("email-1", fields[1].id)
    }
}
