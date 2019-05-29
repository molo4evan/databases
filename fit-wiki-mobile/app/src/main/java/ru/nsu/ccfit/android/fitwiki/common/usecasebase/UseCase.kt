package ru.nsu.ccfit.android.fitwiki.common.usecasebase

import android.util.Log
import ru.nsu.ccfit.android.fitwiki.common.utils.Constants

abstract class UseCase<RequestType: UseCase.RequestValues, ResponseType: UseCase.ResponseValues> {
    lateinit var requestValues: RequestType
    lateinit var useCaseCallback: IUseCaseCallback<ResponseType>

    //region Public interface
    fun run(){
        Log.d(Constants.STACK_TRACE_LOG_TAG, "UseCase.run")
        executeUseCase(requestValues)
    }
    //endregion

    //region Should be overridden
    protected abstract fun executeUseCase(requestValues: RequestType)
    //endregion

    //region Internal types for Request and Response values
    interface RequestValues
    interface ResponseValues
    //endregion
}