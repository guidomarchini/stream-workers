package stream

/**
 * Each worker result. We have three inputs:
 * Success: we don't have elapsed time nor byte_cnt
 * TIMEOUT is reported if that worker exceeds a given time limit
 * FAILURE should be reported for any error/exception of the worker
 */
data class Result private constructor(
    val elapsed: Long? = null,
    val byte_cnt: Long? = null,
    val status: ResultStatus
) {
    companion object {
        /**
         * A successful Result, meaning that the system found the given word.
         */
        fun success(elapsed: Long, byte_cnt: Long): Result =
            Result(
                elapsed = elapsed,
                byte_cnt = byte_cnt,
                status = ResultStatus.SUCCESS
            )

        /**
         * A failed Result, meaning that the system didn't find the result or
         * an error was found while searching for it
         */
        fun failure(): Result =
            Result(status = ResultStatus.FAILURE)

        /**
         * A timeout Result, meaning that the system didn't find the word for the given time.
         */
        fun timeout(): Result =
            Result(status = ResultStatus.TIMEOUT)
    }
}
