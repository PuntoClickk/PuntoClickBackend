package com.puntoclick.features.utils



@Deprecated("Use new  validateStringRequest")
fun String.validateRequestString(maxLength: Int = 30): String? {
    if (isEmpty()) return null
    if (length >= maxLength) return null

    // Validate the string
    val regex = Regex("^[a-zA-Z0-9\\s]*\$") // Only allows letters, numbers, and spaces
    if (!regex.matches(this)) return null // The string contains disallowed characters

    // Process the string to prevent SQL injection
    val processedString = replace("'", "''") // Escape single quotes

    return processedString
}

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
