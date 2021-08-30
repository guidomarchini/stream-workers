package stream

import java.util.*
import java.util.concurrent.atomic.AtomicLong

class Report {
    val results: MutableList<Result> = Collections.synchronizedList(mutableListOf())
    val byteCount: AtomicLong = AtomicLong()
    var elapsedTime: Long = 0

    fun receiveResult(result: Result) {
        results.add(result)
        // only successful results have byte count
        result.byte_cnt?.let { byteCount.addAndGet(it) }
    }
}