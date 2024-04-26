package com.puntoclick.features.utils


@Suppress("unused")
fun String.isStringAllowed(): Boolean {
    val regex = Regex("^[a-zA-Z0-9\\s]*\$")
    return regex.matches(this)
}
@Suppress("unused")
fun String.preventSQLInjection(): String = replace("'", "''")

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

fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    return emailRegex.matches(email)
}
