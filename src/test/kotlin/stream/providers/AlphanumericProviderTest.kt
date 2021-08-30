package stream.providers

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class AlphanumericProviderTest {
    @Test
    fun test_getWords() {
        // arrange
        val provider: DataProvider = AlphanumericProvider()

        // act - assert
        assertTrue(provider.hasNext())
        assertNotNull(provider.nextElement())
    }
}