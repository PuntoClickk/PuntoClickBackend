package com.puntoclick.security

interface AppEncryption {
    fun encrypt(plainText: String): String
    fun decrypt(encryptedText: String): String
}