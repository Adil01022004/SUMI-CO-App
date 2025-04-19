package com.example.sumico

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import android.util.Base64

object HandshakeEncryption {
    private const val SECRET_KEY = "mySuperSecret123" // заменить на результат рукопожатия
    private const val ALGORITHM = "AES"

    fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(), ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)
    }

    fun decrypt(data: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(), ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        val decodedBytes = Base64.decode(data, Base64.NO_WRAP)
        return String(cipher.doFinal(decodedBytes))
    }
}
