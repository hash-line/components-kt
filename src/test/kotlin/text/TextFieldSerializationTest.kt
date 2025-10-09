package text

import Presence
import ValidationCode
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TextFieldSerializationTest {

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    @Test
    fun `test TextField deserialization`() {
        // Given
        val jsonString = """
        {
            "id": "test-field-2",
            "required": true,
            "readOnly": false,
            "singleLine": true,
            "placeHolder": "Enter email",
            "label": "Email Address",
            "value": "test@example.com",
            "minLength": 5,
            "maxLength": 100,
            "presence": "Visible",
            "enabled": true
        }
        """.trimIndent()

        // When
        val textField = json.decodeFromString<TextField>(jsonString)

        // Then
        assertEquals("test-field-2", textField.id)
        assertEquals(true, textField.required)
        assertEquals(false, textField.readOnly)
        assertEquals(true, textField.singleLine)
        assertEquals("Enter email", textField.placeHolder)
        assertEquals("Email Address", textField.label)
        assertEquals(5, textField.minLength)
        assertEquals(100, textField.maxLength)
        assertEquals(Presence.Visible, textField.presenceFlow.value)
        assertEquals(true, textField.enabledFlow.value)
    }

    @Test
    fun `test TextField serialization and deserialization round trip`() {
        // Given
        val originalTextField = TextField(
            id = "round-trip-test",
            required = false,
            readOnly = true,
            singleLine = false,
            placeHolder = "Multi-line input",
            label = "Description",
            value = "This is a test value",
            minLength = 10,
            maxLength = 200,
            presence = Presence.Hidden,
            enabled = false
        )

        // When
        val jsonString = json.encodeToString<TextInputField>(originalTextField)
        val deserializedTextField = json.decodeFromString<TextField>(jsonString)

        // Then
        assertEquals(originalTextField.id, deserializedTextField.id)
        assertEquals(originalTextField.required, deserializedTextField.required)
        assertEquals(originalTextField.readOnly, deserializedTextField.readOnly)
        assertEquals(originalTextField.singleLine, deserializedTextField.singleLine)
        assertEquals(originalTextField.placeHolder, deserializedTextField.placeHolder)
        assertEquals(originalTextField.label, deserializedTextField.label)
        assertEquals(originalTextField.minLength, deserializedTextField.minLength)
        assertEquals(originalTextField.maxLength, deserializedTextField.maxLength)
        assertEquals(originalTextField.presenceFlow.value, deserializedTextField.presenceFlow.value)
        assertEquals(originalTextField.enabledFlow.value, deserializedTextField.enabledFlow.value)
    }

    @Test
    fun `test TextField with empty string values`() {
        // Given
        val textField = TextField(
            id = "",
            placeHolder = "",
            label = "",
            value = ""
        )

        // When
        val jsonString = json.encodeToString(textField)
        val deserializedTextField = json.decodeFromString<TextField>(jsonString)

        // Then
        assertEquals("", deserializedTextField.id)
        assertEquals("", deserializedTextField.placeHolder)
        assertEquals("", deserializedTextField.label)
    }

    @Test
    fun `test TextField with boundary length values`() {
        // Given
        val textField = TextField(
            minLength = 0,
            maxLength = Int.MAX_VALUE
        )

        // When
        val jsonString = json.encodeToString(textField)
        val deserializedTextField = json.decodeFromString<TextField>(jsonString)

        // Then
        assertEquals(0, deserializedTextField.minLength)
        assertEquals(Int.MAX_VALUE, deserializedTextField.maxLength)
    }

    @Test
    fun `test TextField with different presence values`() {
        // Test Visible presence
        val visibleField = TextField(presence = Presence.Visible)
        val visibleJson = json.encodeToString(visibleField)
        val deserializedVisible = json.decodeFromString<TextField>(visibleJson)
        assertEquals(Presence.Visible, deserializedVisible.presenceFlow.value)

        // Test Hidden presence
        val hiddenField = TextField(presence = Presence.Hidden)
        val hiddenJson = json.encodeToString(hiddenField)
        val deserializedHidden = json.decodeFromString<TextField>(hiddenJson)
        assertEquals(Presence.Hidden, deserializedHidden.presenceFlow.value)

        // Test Forgotten presence
        val forgottenField = TextField(presence = Presence.Forgotten)
        val forgottenJson = json.encodeToString(forgottenField)
        val deserializedForgotten = json.decodeFromString<TextField>(forgottenJson)
        assertEquals(Presence.Forgotten, deserializedForgotten.presenceFlow.value)
    }

    @Test
    fun `test TextValidation serialization`() {
        // Given
        val validation = TextValidation(
            minLength = 5,
            maxLength = 100
        )

        // When
        val jsonString = json.encodeToString(validation)

        // Then
        assertNotNull(jsonString)
        assertTrue(jsonString.contains("\"minLength\": 5"))
        assertTrue(jsonString.contains("\"maxLength\": 100"))
    }

    @Test
    fun `test TextValidation deserialization`() {
        // Given
        val jsonString = """
        {
            "minLength": 3,
            "maxLength": 50
        }
        """.trimIndent()

        // When
        val validation = json.decodeFromString<TextValidation>(jsonString)

        // Then
        assertNotNull(validation)
        // Test validation behavior instead of accessing private properties
        assertEquals(ValidationCode.EMPTY, validation.validate(""))
        assertEquals(ValidationCode.INVALID_LENGTH, validation.validate("ab")) // Too short
        assertEquals(ValidationCode.VALID, validation.validate("abc")) // Valid length
    }

    @Test
    fun `test TextValidation serialization and deserialization round trip`() {
        // Given
        val originalValidation = TextValidation(
            minLength = 10,
            maxLength = 200
        )

        // When
        val jsonString = json.encodeToString(originalValidation)
        val deserializedValidation = json.decodeFromString<TextValidation>(jsonString)

        // Then
        assertNotNull(deserializedValidation)
        // Test validation behavior instead of accessing private properties
        assertEquals(ValidationCode.EMPTY, deserializedValidation.validate(""))
        assertEquals(ValidationCode.INVALID_LENGTH, deserializedValidation.validate("short")) // Too short
        assertEquals(ValidationCode.VALID, deserializedValidation.validate("This is a valid string with more than 10 characters"))
    }

    @Test
    fun `test TextField with special characters in values`() {
        // Given
        val specialValue = "Test with special chars: !@#$%^&*()_+-=[]{}|;':\",./<>?"
        val textField = TextField(
            id = "special-chars-test",
            value = specialValue,
            placeHolder = "Enter special chars",
            label = "Special Characters Test"
        )

        // When
        val jsonString = json.encodeToString(textField)
        val deserializedTextField = json.decodeFromString<TextField>(jsonString)

        // Then
        assertEquals(specialValue, deserializedTextField.valueFlow.value)
        assertEquals("Enter special chars", deserializedTextField.placeHolder)
        assertEquals("Special Characters Test", deserializedTextField.label)
    }

    @Test
    fun `test TextField with unicode characters`() {
        // Given
        val unicodeValue = "Hello 世界 🌍 مرحبا بالعالم"
        val textField = TextField(
            id = "unicode-test",
            value = unicodeValue,
            placeHolder = "Enter unicode text",
            label = "Unicode Test"
        )

        // When
        val jsonString = json.encodeToString(textField)
        val deserializedTextField = json.decodeFromString<TextField>(jsonString)

        // Then
        assertEquals(unicodeValue, deserializedTextField.valueFlow.value)
        assertEquals("Enter unicode text", deserializedTextField.placeHolder)
        assertEquals("Unicode Test", deserializedTextField.label)
    }
}
