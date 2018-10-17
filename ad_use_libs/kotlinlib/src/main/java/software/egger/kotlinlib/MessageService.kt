package software.egger.kotlinlib

import java.util.*

class MessageService(private val locale: Locale) {

    fun helloWorldMessage() = when (locale) {
        Locale.GERMAN -> "Hallo Welt"
        else -> "Hello World"
    }

}