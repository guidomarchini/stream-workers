package stream

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import stream.providers.DataProvider
import stream.providers.TextProvider
import kotlin.test.Test

internal class WorkerTest {
    private val textToFind: String = "Lpfn"

    @Test
    fun test_onlyText() {
        // arrange
        val provider: DataProvider = TextProvider(text = textToFind)
        val worker: Worker = Worker(provider = provider)

        runBlocking {
            // act
            val result: Result = worker.searchText(textToFind = textToFind)

            // assert
            assertEquals(ResultStatus.SUCCESS, result.status)
            assertNotNull(result.elapsed)
            assertEquals(4, result.byte_cnt)
        }
    }

    @Test
    fun test_wordBetweenWholeString() {
        // arrange
        val contentPrefix: String = "s0m3Str1ng"
        val contentSufix: String = "4ndAn0th3rStr1ng"
        val providedContent: String = contentPrefix + textToFind + contentSufix
        val provider: DataProvider = TextProvider(text = providedContent)
        val worker: Worker = Worker(provider = provider)

        runBlocking {
            // act
            val result: Result = worker.searchText(textToFind = textToFind)

            // assert
            assertEquals(ResultStatus.SUCCESS, result.status)
            assertNotNull(result.elapsed)
            assertEquals(4L + contentPrefix.length, result.byte_cnt)
        }
    }
}