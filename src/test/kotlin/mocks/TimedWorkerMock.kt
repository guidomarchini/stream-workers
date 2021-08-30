package mocks

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import stream.Result
import stream.Worker
import stream.providers.TextProvider

class TimedWorkerMock constructor(
    private val result: Result,
    private val suspendTime: Long
) : Worker(TextProvider("")) {
        override suspend fun searchText(text: String): Result = coroutineScope {
            delay(suspendTime)
            result
        }
    }