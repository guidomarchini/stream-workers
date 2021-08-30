package stream

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlin.system.measureTimeMillis

class Boss {
    /**
     * Boss orders its workers to find for the given word.
     * It spawns a coroutine for every worker in order to search for the text.
     * @param textToFind the text that the workers will need to find
     * @param workers the workers that will be put to work
     * @param timeout the workers execution timeout in millis.
     */
    fun orchestrateWork(
        textToFind: String,
        workers: List<Worker>,
        timeout: Long
    ): Report = runBlocking {
        val report: Report = Report()

        report.elapsedTime = measureTimeMillis {
            workers.forEach { worker ->
                try {
                    withTimeout(timeout) {
                        launch {
                            report.receiveResult(
                                worker.searchText(
                                    textToFind = textToFind
                                )
                            )
                        }
                    }
                } catch (e: TimeoutCancellationException) {
                    report.receiveResult(Result.timeout())
                } catch (e: Exception) {
                    report.receiveResult(Result.failure())
                }
            }
        }

        report
    }
}