package svcs

import java.security.MessageDigest


fun main() {
    println("Test Encryption")

    fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }

    fun String.toMD5(): String {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.toHex()
    }
    fun String.toSHA256(): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(this.toByteArray())
        return bytes.toHex()
    }
    fun HashArraySHA256(obj: Array<Any>): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(obj)
    }



    val arrayString = arrayOf("Hola", "Mundo")


    println("Hola".toMD5())
    println("Hola".toSHA256())
}