package mocks

import stream.Result
import stream.Worker
import stream.providers.TextProvider

/**
 * I don't know why mockito isn't mocking my classes :(
 */
class WorkerMock constructor(private val result: Result) : Worker(TextProvider("")) {
    override suspend fun searchText(text: String): Result = result
}