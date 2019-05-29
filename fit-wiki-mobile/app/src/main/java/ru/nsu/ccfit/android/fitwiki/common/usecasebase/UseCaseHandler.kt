package ru.nsu.ccfit.android.fitwiki.common.usecasebase

import android.util.Log
import ru.nsu.ccfit.android.fitwiki.common.utils.Constants

class UseCaseHandler private constructor(){
    private val useCaseScheduler: IUseCaseScheduler

    init {
        Log.d(Constants.STACK_TRACE_LOG_TAG, "UseCaseHandler.init")
        useCaseScheduler = UseCaseThreadPoolScheduler()
    }

    private object Holder {
        val INSTANCE = UseCaseHandler()
    }

    companion object {
        val instance by lazy { Holder.INSTANCE }
    }

    fun <T: UseCase.RequestValues, R: UseCase.ResponseValues> execute(
            useCase: UseCase<T, R>,
            requestValues: T,
            callback: IUseCaseCallback<R>
    ){
        Log.d(Constants.STACK_TRACE_LOG_TAG, "UseCaseHandler.execute")

        useCase.requestValues = requestValues
        useCase.useCaseCallback = UseCaseCallbackWrapper(callback, this)

        useCaseScheduler.exectue(Runnable {
            useCase.run()
        })
    }

    fun <T: UseCase.ResponseValues> notifyResponse(response: T, useCaseCallback: IUseCaseCallback<T>){
        Log.d(Constants.STACK_TRACE_LOG_TAG, "UseCaseHandler.notifyResponse")
        useCaseScheduler.onResponce(response, useCaseCallback)
    }

    fun <T: UseCase.ResponseValues> notifyError(useCaseCallback: IUseCaseCallback<T>){
        Log.d(Constants.STACK_TRACE_LOG_TAG, "UseCaseHandler.notifyError")
        useCaseScheduler.onError(useCaseCallback)
    }
}