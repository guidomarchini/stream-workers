package stream

import kotlinx.coroutines.runBlocking
import mocks.TimedWorkerMock
import mocks.WorkerMock
import kotlin.test.*

internal class BossTest {
    private val textToFind: String = "Lpfn"

    @Test
    fun test_workerSuccess() {
        // arrange
        val workerBoss: Boss = Boss()
        val workerResult = Result.success(elapsed = 1L, byte_cnt = 10)
        val workerMock: Worker = WorkerMock(workerResult)

        runBlocking {
            // act
            val report: Report = workerBoss.orchestrateWork(
                textToFind = textToFind,
                workers = listOf(workerMock),
                timeout = 50,
            )

            // assert
            assertEquals(1, report.results.size)
            assertEquals(workerResult, report.results.first())
        }
    }

    @Test
    fun test_multipleWorkers() {
        // arrange
        val workerBoss: Boss = Boss()

        val successfulResult: Result = Result.success(elapsed = 1L, byte_cnt = 10)
        val failureResult: Result = Result.failure()
        val timeoutResult: Result = Result.timeout()

        val successWorker: Worker = WorkerMock(successfulResult)
        val failureWorker: Worker = WorkerMock(failureResult)
        val timeoutWorker: Worker = WorkerMock(timeoutResult)

        runBlocking {
            // act
            val report: Report = workerBoss.orchestrateWork(
                timeout = 1000,
                textToFind = textToFind,
                workers = listOf(successWorker, timeoutWorker, failureWorker)
            )

            // assert
            assertNotNull(report.elapsedTime)
            assertEquals(3, report.results.size)
            assertContains(report.results, successfulResult)
            assertContains(report.results, failureResult)
            assertContains(report.results, timeoutResult)
        }
    }

    @Test
    fun test_workerSuspended() {
        // arrange
        val workerBoss: Boss = Boss()
        val workerResult = Result.success(elapsed = 1L, byte_cnt = 10)
        val timeout: Long = 10
        val workerMock: Worker = TimedWorkerMock(suspendTime = timeout+10, result = workerResult)

        runBlocking {
            // act
            val report: Report = workerBoss.orchestrateWork(
                textToFind = textToFind,
                workers = listOf(workerMock),
                timeout = timeout,
            )

            // assert
            assertTrue(report.elapsedTime > timeout)
            assertEquals(1, report.results.size)
            assertNotEquals(workerResult, report.results.first())
            assertEquals(ResultStatus.TIMEOUT, report.results.first().status)
        }
    }
}