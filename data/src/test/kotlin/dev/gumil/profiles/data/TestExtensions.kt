package dev.gumil.profiles.data

import okhttp3.mockwebserver.MockResponse
import java.io.ByteArrayOutputStream
import java.lang.IllegalStateException

private const val BUFFER_SIZE = 1024
private const val SUCCESS_RESPONSE_CODE = 200

private fun Any.readFromFile(file: String): String {
    val inputStream = this.javaClass.classLoader?.getResourceAsStream(file)
        ?: throw IllegalStateException("Failed to load the classLoader")
    val result = ByteArrayOutputStream()
    val buffer = ByteArray(BUFFER_SIZE)
    var length = inputStream.read(buffer)
    while (length != -1) {
        result.write(buffer, 0, length)
        length = inputStream.read(buffer)
    }
    return result.toString("UTF-8")
}

fun Any.mockResponseFrom(file: String): MockResponse {
    val body = readFromFile(file)
    return MockResponse().apply {
        setResponseCode(SUCCESS_RESPONSE_CODE)
        setBody(body)
        addHeader("Content-type: application/json")
    }
}
