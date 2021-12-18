import java.io.File
import java.lang.RuntimeException
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String): List<String> {
    val file = File("src", "$name.txt")
    if (!file.exists()) {
        println("Creating new empty file ${file.name}")
        file.createNewFile()
    }
    return file.readLines()
}

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

infix fun Any.isEqualTo(expected: Any): Boolean {
    if(!this.equals(expected)) {
        throw RuntimeException("Expected $expected, but got $this")
    }
    println(this)
    return true
}
