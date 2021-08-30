package stream.providers

/**
 * Provider that generates a character by randomly generating a byte array.
 */
class AlphanumericProvider : DataProvider() {
    private val allowedChars: List<Char> = ('A'..'Z') + ('a'..'z') + ('0'..'9')

    override fun nextElement(): String {
        return this.allowedChars.random().toString()
    }

    override fun hasNext(): Boolean = true
}