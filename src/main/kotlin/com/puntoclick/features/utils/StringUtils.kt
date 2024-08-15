package com.puntoclick.features.utils


fun String.validateStringRequest(maxLength: Int = 30): Boolean {
    return  when{
        isEmpty() -> false
        (length >= maxLength) -> false
        !isStringValid() -> false
        else -> true
    }
}

private fun String.isStringValid(): Boolean {
    val regex = Regex("^[a-zA-Z0-9\\s]*\$") // Only allows letters, numbers, and spaces
    return regex.matches(this)
}


fun String.escapeSingleQuotes(): String = replace("'", "''")


fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    return emailRegex.matches(email)
}

fun isValidCellPhoneNumber(phoneNumber: String): Boolean {
    val phoneNumberRegex = Regex("^\\+?[1-9]\\d{1,14}$")
    return phoneNumberRegex.matches(phoneNumber)
}

fun String.maskString(numberOfCharactersVisible: Int = 3): String {
    if (length < numberOfCharactersVisible) return this
    val lastThree = takeLast(numberOfCharactersVisible)
    val maskedPart = "*".repeat(length - numberOfCharactersVisible)
    return "$maskedPart$lastThree"
}

