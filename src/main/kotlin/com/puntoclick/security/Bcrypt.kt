package com.puntoclick.security

import at.favre.lib.crypto.bcrypt.BCrypt

fun String.hashPassword(): String {
    return BCrypt.withDefaults().hashToString(12, toCharArray())
}

fun String.verifyPassword(hashedPassword: String): Boolean {
    val result = BCrypt.verifyer().verify(toCharArray(), hashedPassword)
    return result.verified
}
