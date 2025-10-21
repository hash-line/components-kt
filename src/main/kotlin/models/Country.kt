package models

import kotlinx.serialization.Serializable

@Serializable
sealed class Country(
    open val name: String
) {
    class Pakistan : Country("PK")
    class India : Country("IN")
    class USA : Country("USA")
    class UK : Country("UK")
    class KSA : Country("KSA")
    data object All : Country("All")

    companion object {
        val default = Pakistan()
    }
}
fun Country.mobileNumberRegex(): Regex = when (this) {
    is Country.Pakistan -> Regex("^((\\+92)|0)?[3][0-9]{9}$")
    is Country.India -> Regex("^((\\+91)|0)?[6-9][0-9]{9}$")
    is Country.USA -> Regex("^((\\+1)|1)?[2-9][0-9]{9}$") // Supports both +1 and 1
    is Country.UK -> Regex("^((\\+44)|0)?7[0-9]{9}$")
    is Country.KSA -> Regex("^((\\+966)|0)?5[0-9]{8}$") // Updated regex for KSA

    is Country.All -> Regex("^\\+${Regex.escape(code())}[0-9]{6,15}$") // Generic international format

}


fun Country.isValidLength(prefixCountryCode: Boolean): Int {
    return when (this) {
        is Country.Pakistan -> if (prefixCountryCode) 10 else 11
        is Country.India -> if (prefixCountryCode) 10 else 11
        is Country.UK -> if (prefixCountryCode) 10 else 11
        is Country.USA -> 10
        is Country.KSA -> 10

        else -> Int.MAX_VALUE // Default for generic international format
    }
}


fun Country.code(): String = when (this) {
    is Country.Pakistan -> "+92"
    is Country.India -> "+91"
    is Country.USA -> "+1"
    is Country.UK -> "+44"
    is Country.KSA -> "+966"


    is Country.All -> "All"
}
