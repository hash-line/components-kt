package text

import Component
import ValidationCode
import Presence
import models.Country
import models.code
import models.mobileNumberRegex

/**
 * [MsisdnField] is input for MSISDN (Mobile Station International Subscriber Directory Number)
 *
 * @property countryCode The country code of the MSISDN
 * @property prefixCountryCode Whether the country code should be appended to the MSISDN
 */
class MsisdnField(
    id: String = "Mobile",
    value: String = "",
    required: Boolean = true,
    readOnly: Boolean = false,
    presence: Presence = Presence.Visible,
    placeHolder: String = "",
    top: Component? = null,
    bottom: Component? = null,
    start: Component? = null,
    end: Component? = null,
    val countryCode: Country = Country.default,
    val prefixCountryCode: Boolean = false
) : TextInputField(
    id = id,
    value = if (prefixCountryCode && value.startsWith("0")) {
        value.drop(1)
    } else {
        value
    },
    top = top,
    bottom = bottom,
    start = start,
    end = end,
    required = required,
    readOnly = readOnly,
    presence = presence,
    singleLine = true,
    placeHolder = placeHolder,
    validation = MsisdnValidation(
        countryCode = countryCode,
        countryCodeSuffixed = prefixCountryCode
    )
) {
    val withCountryCode: String
        get() {
            val enteredValue = value.value
            return if (prefixCountryCode) {
                "${countryCode.code()}${enteredValue}"
            } else {
                enteredValue
            }
        }

    val withoutCountryCode: String
        get() {
            val enteredValue = value.value
            return if (enteredValue.isEmpty()) ""
            else if (enteredValue.startsWith("0"))
                enteredValue
            else
                "0${enteredValue}"
        }

    override suspend fun setValue(value: String) {
        //If prefixCountryCode is true, we remove the '0' from the input
        //FIXME: Add removal of country code as well
        val newValue = if (prefixCountryCode && value.startsWith("0")) {
            value.drop(1)
        } else {
            value
        }
        super.setValue(newValue)
    }
}


class MsisdnValidation(
    private val countryCode: Country,
    private val countryCodeSuffixed: Boolean = false
) : TextInputValidation {

    // Fix MSISDN field validation
    override fun validate(input: String): Int {
        // If countryCodeSuffixed is true, we append the country code to the input
        val cleanedInput = if (countryCodeSuffixed) {
            "${countryCode.code()}$input"
        } else {
            input
        }

        return when {
            cleanedInput.isEmpty() -> ValidationCode.EMPTY // Return EMPTY if cleaned input is empty
            //!countryCode.isValidLength(countryCodeSuffixed) -> ValidationCode.INVALID_LENGTH // Check for invalid length
            !countryCode.mobileNumberRegex()
                .matches(cleanedInput) -> ValidationCode.INVALID_FORMAT // Check for invalid format
            else -> ValidationCode.VALID // Valid input

        }
    }
}
