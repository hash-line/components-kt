package selection

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SelectionInputModuleTest {

    private val json = Json {
        prettyPrint = true
        encodeDefaults = true
        ignoreUnknownKeys = true
        serializersModule = SelectionInputModule
    }

    @Test
    fun `test polymorphic serialization of DropDownField`() {
        // Given
        val dropDownField = DropDownField(
            id = "dropdown-test",
            placeHolder = "Select an option",
            optionsLabel = "Choose Option"
        )

        // When
        val jsonString = json.encodeToString<SelectionInputField>(dropDownField)

        // Then
        assertNotNull(jsonString)
        assertTrue(jsonString.contains("\"type\": \"selection.DropDownField\""))
        assertTrue(jsonString.contains("\"id\": \"dropdown-test\""))
        assertTrue(jsonString.contains("\"placeHolder\": \"Select an option\""))
        assertTrue(jsonString.contains("\"optionsLabel\": \"Choose Option\""))
    }

    @Test
    fun `test polymorphic deserialization of DropDownField`() {
        // Given
        val jsonString = """
        {
            "type": "selection.DropDownField",
            "id": "dropdown-test",
            "required": true,
            "readOnly": false,
            "placeHolder": "Select option",
            "optionsLabel": "Options",
            "presence": "Visible",
            "enabled": true
        }
        """.trimIndent()

        // When
        val dropDownField = json.decodeFromString<SelectionInputField>(jsonString)

        // Then
        assertNotNull(dropDownField)
        assertTrue(dropDownField is DropDownField)
        assertEquals("dropdown-test", dropDownField.id)
        assertEquals("Select option", (dropDownField as DropDownField).placeHolder)
        assertEquals("Options", dropDownField.optionsLabelFlow.value)
    }

    @Test
    fun `test polymorphic serialization of MultiChoiceField`() {
        // Given
        val multiChoiceField = MultiChoiceField(
            id = "multichoice-test",
            placeHolder = "Choose one",
            searchable = true,
            orientation = Orientation.Horizontal
        )

        // When
        val jsonString = json.encodeToString<SelectionInputField>(multiChoiceField)

        // Then
        assertNotNull(jsonString)
        assertTrue(jsonString.contains("\"type\": \"selection.MultiChoiceField\""))
        assertTrue(jsonString.contains("\"id\": \"multichoice-test\""))
        assertTrue(jsonString.contains("\"placeHolder\": \"Choose one\""))
        assertTrue(jsonString.contains("\"searchable\": true"))
        assertTrue(jsonString.contains("\"orientation\": \"Horizontal\""))
    }

    @Test
    fun `test polymorphic deserialization of MultiChoiceField`() {
        // Given
        val jsonString = """
        {
            "type": "selection.MultiChoiceField",
            "id": "multichoice-test",
            "required": true,
            "readOnly": false,
            "placeHolder": "Select option",
            "searchable": false,
            "orientation": "Vertical",
            "presence": "Visible",
            "enabled": true
        }
        """.trimIndent()

        // When
        val multiChoiceField = json.decodeFromString<SelectionInputField>(jsonString)

        // Then
        assertNotNull(multiChoiceField)
        assertTrue(multiChoiceField is MultiChoiceField)
        assertEquals("multichoice-test", multiChoiceField.id)
        assertEquals("Select option", (multiChoiceField as MultiChoiceField).placeHolder)
        assertEquals(false, multiChoiceField.searchable)
        assertEquals(Orientation.Vertical, multiChoiceField.orientation)
    }

    @Test
    fun `test polymorphic serialization of AcknowledgementField`() {
        // Given
        val acknowledgementField = AcknowledgementField(
            id = "ack-test",
            label = null
        )

        // When
        val jsonString = json.encodeToString<SelectionInputField>(acknowledgementField)

        // Then
        assertNotNull(jsonString)
        assertTrue(jsonString.contains("\"type\": \"selection.AcknowledgementField\""))
        assertTrue(jsonString.contains("\"id\": \"ack-test\""))
        assertTrue(jsonString.contains("\"label\": null"))
    }

    @Test
    fun `test polymorphic serialization round trip`() {
        // Given
        val originalField = DropDownField(
            id = "round-trip-test",
            placeHolder = "Round Trip Test",
            optionsLabel = "Test Options"
        )

        // When
        val jsonString = json.encodeToString<SelectionInputField>(originalField)
        val deserializedField = json.decodeFromString<SelectionInputField>(jsonString)

        // Then
        assertNotNull(deserializedField)
        assertTrue(deserializedField is DropDownField)
        assertEquals(originalField.id, deserializedField.id)
        assertEquals(originalField.placeHolder, deserializedField.placeHolder)
        assertEquals(originalField.optionsLabelFlow.value, deserializedField.optionsLabelFlow.value)
    }

    @Test
    fun `test polymorphic serialization with mixed field types`() {
        // Given
        val fields = listOf(
            DropDownField(id = "dropdown-1", placeHolder = "Dropdown"),
            MultiChoiceField(id = "multichoice-1", placeHolder = "Multi Choice"),
            AcknowledgementField(id = "ack-1", label = null)
        )

        // When
        val jsonString = json.encodeToString<List<SelectionInputField>>(fields)

        // Then
        assertNotNull(jsonString)
        assertTrue(jsonString.contains("\"type\": \"selection.DropDownField\""))
        assertTrue(jsonString.contains("\"type\": \"selection.MultiChoiceField\""))
        assertTrue(jsonString.contains("\"type\": \"selection.AcknowledgementField\""))
    }

    @Test
    fun `test polymorphic deserialization with mixed field types`() {
        // Given
        val jsonString = """
        [
            {
                "type": "selection.DropDownField",
                "id": "dropdown-1",
                "placeHolder": "Select option",
                "optionsLabel": "Options",
                "required": true,
                "readOnly": false,
                "presence": "Visible",
                "enabled": true
            },
            {
                "type": "selection.MultiChoiceField",
                "id": "multichoice-1",
                "placeHolder": "Choose option",
                "searchable": true,
                "orientation": "Horizontal",
                "required": true,
                "readOnly": false,
                "presence": "Visible",
                "enabled": true
            }
        ]
        """.trimIndent()

        // When
        val fields = json.decodeFromString<List<SelectionInputField>>(jsonString)

        // Then
        assertNotNull(fields)
        assertEquals(2, fields.size)
        assertTrue(fields[0] is DropDownField)
        assertTrue(fields[1] is MultiChoiceField)
        assertEquals("dropdown-1", fields[0].id)
        assertEquals("multichoice-1", fields[1].id)
    }
}
