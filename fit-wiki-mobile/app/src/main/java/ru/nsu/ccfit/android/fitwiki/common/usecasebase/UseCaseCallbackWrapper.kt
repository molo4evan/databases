package ru.nsu.ccfit.android.fitwiki.common.usecasebase

import android.util.Log
import ru.nsu.ccfit.android.fitwiki.common.utils.Constants

class UseCaseCallbackWrapper<T: UseCase.ResponseValues>(
        private val callback: IUseCaseCallback<T>,
        private val useCaseHandler: UseCaseHandler
): IUseCaseCallback<T> {
    init {
        Log.d(Constants.STACK_TRACE_LOG_TAG, "UseCaseCallbsckWrapper.init")
    }

    //region IUseCaseCallback
    override fun onSuccess(response: T) {
        Log.d(Constants.STACK_TRACE_LOG_TAG, "UseCaseCallbackWrapper.onSuccess")
        useCaseHandler.notifyResponse(response, callback)
    }

    override fun onError() {
        Log.d(Constants.STACK_TRACE_LOG_TAG, "UseCaseCallbackWrapper.onError")
        useCaseHandler.notifyError(callback)
    }
    //endregion

}