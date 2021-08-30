package app

import stream.Boss
import stream.Report
import stream.ResultStatus
import stream.Worker
import stream.providers.AlphanumericProvider

private const val WORKERS_QUANTITY: Int = 10
private const val DEFAULT_TIMEOUT: Long = 60*1000
private const val TEXT_TO_FIND: String = "Lpfn"

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        val timeout: Long = if (args.isNotEmpty()) args[0].toLong() else DEFAULT_TIMEOUT
        val workerBoss: Boss = Boss()
        val workers: List<Worker> = List(WORKERS_QUANTITY) { Worker(AlphanumericProvider()) }

        val report: Report = workerBoss.orchestrateWork(
            textToFind = TEXT_TO_FIND,
            workers = workers,
            timeout = timeout
        )

        processResults(report)
    }

    private fun processResults(report: Report) {
        report.results.sortedBy { it.elapsed }.forEach { result ->
            when(result.status) {
                ResultStatus.SUCCESS ->
                    println("Result status: ${result.status}. Bytes read: ${result.byte_cnt}. Elapsed time: ${result.elapsed}")
                else ->
                    println("Result status ${result.status}")
            }
        }
        println("Total time spent: ${report.elapsedTime}. Bytes read per millis: ${report.byteCount.get() / report.elapsedTime}")
    }
}
