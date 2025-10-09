interface InputFieldEvent: Event

data class InputFieldValueChangeEvent<T>(
    val field: InputField<T>,
    val validationState: Int,
    val value: T
) : InputFieldEvent


