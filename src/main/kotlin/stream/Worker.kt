package stream

import kotlinx.coroutines.coroutineScope
import stream.providers.DataProvider
import kotlin.system.measureTimeMillis

/**
 * A worker tries to find the given text, getting strings from the given provider.
 */
open class Worker constructor(val provider: DataProvider) {
    /**
     * Searches for the given text using the provider to retrieve elements, and posts the result to the report.
     */
    open suspend fun searchText(textToFind: String): Result = coroutineScope {
        // initialize
        var bytesCount: Long = 0
        val wordBuilder: StringBuilder = StringBuilder()

        // process
        val elapsed: Long = measureTimeMillis {
            while(!textFound(textToFind, wordBuilder)) {
                bytesCount++
                processNextElement(
                    textToFind = textToFind,
                    builder = wordBuilder,
                    nextElement = provider.nextElement()
                )
            }
        }

        // prepare response
        Result.success(byte_cnt = bytesCount, elapsed = elapsed)
    }

    private fun textFound(textToFind: String, wordBuilder: StringBuilder) = wordBuilder.toString() == textToFind

    /**
     * Process the given element.
     * This maintains the builder with the same length as the word to find
     */
    private fun processNextElement(
        textToFind: String,
        builder: StringBuilder,
        nextElement: String
    ): Unit {
        if (builder.length == textToFind.length)
            builder.deleteAt(0)
        builder.append(nextElement)
    }
}