package stream.providers

/**
 * Abstraction for the provider.
 * You can ask for data to the provider :)
 */
abstract class DataProvider {
    /**
     * Provides a new element (string with length 1).
     * @return String a new element
     */
    abstract fun nextElement(): String

    /**
     * Returns whether this provider has a next element.
     * Not really a part of the statement, but i wanted to play a little :)
     * @return Boolean true if it has an element. False otherwise
     */
    abstract fun hasNext(): Boolean
}