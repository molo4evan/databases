package ru.nsu.ccfit.android.fitwiki.common.usecasebase

import android.os.Handler
import android.util.Log
import ru.nsu.ccfit.android.fitwiki.common.utils.Constants
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class UseCaseThreadPoolScheduler: IUseCaseScheduler {

    //region Private entities
    private val executor = ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIMEOUT,
            TimeUnit.SECONDS, ArrayBlockingQueue<Runnable>(POOL_SIZE))

    private val handler = Handler()
    //endregion

    //region Constants
    private companion object {
        const val POOL_SIZE = 2
        const val MAX_POOL_SIZE = 4
        const val TIMEOUT = 30L
    }
    //endregion

    //region Initialization
    init {
        Log.d(Constants.STACK_TRACE_LOG_TAG, "UseCaseThreadPoolScheduler.init")
    }
    //endregion

    //region IUseCaseScheduler
    override fun exectue(runnable: Runnable) {
        Log.d(Constants.STACK_TRACE_LOG_TAG, "UseCaseThreadPoolScheduler.execute")
        executor.execute(runnable)
    }

    override fun <T : UseCase.ResponseValues> onResponce(responce: T, callback: IUseCaseCallback<T>) {
        Log.d(Constants.STACK_TRACE_LOG_TAG, "UseCaseThreadPoolScheduler.onResponse")
        handler.post {
            Log.d(Constants.STACK_TRACE_LOG_TAG, "UseCaseThreadPoolScheduler.onResponse.run")
            callback.onSuccess(responce)
        }
    }

    override fun <T : UseCase.ResponseValues> onError(callback: IUseCaseCallback<T>) {
        Log.d(Constants.STACK_TRACE_LOG_TAG, "UseCaseThreadPoolScheduler.onError")
        handler.post {
            Log.d(Constants.STACK_TRACE_LOG_TAG, "UseCaseThreadPoolScheduler.onError.run")
            callback.onError()
        }
    }
    //endregion
}