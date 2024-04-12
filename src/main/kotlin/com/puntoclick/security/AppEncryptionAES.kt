package com.puntoclick.security

import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

class AppEncryptionAES: AppEncryption {

    private val key = System.getenv()["PASSWORD_ENC"].orEmpty()

    override fun encrypt(plainText: String): String {
        println(key)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val decodedKey = Base64.getDecoder().decode(key)
        val secretKey: SecretKey = SecretKeySpec(decodedKey, "AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encryptedBytes = cipher.doFinal(plainText.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes) + ":" + Base64.getEncoder().encodeToString(iv)
    }

    override fun decrypt(encryptedText: String): String {
        val parts = encryptedText.split(":")
        val encryptedData = Base64.getDecoder().decode(parts[0])
        val iv = Base64.getDecoder().decode(parts[1])
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val decodedKey = Base64.getDecoder().decode(key)
        val secretKey: SecretKey = SecretKeySpec(decodedKey, "AES")
        val gcmParameterSpec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec)
        val decryptedBytes = cipher.doFinal(encryptedData)
        return String(decryptedBytes)
    }
}