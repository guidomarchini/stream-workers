package stream.providers

/**
 * Provides the word "Lpfn".
 * Made for playing a little :)
 */
class TextProvider constructor(var text: String): DataProvider() {
    override fun nextElement(): String {
        val element = text.take(1)
        text = text.drop(1)
        return element
    }

    override fun hasNext(): Boolean = text.isNotEmpty()
}