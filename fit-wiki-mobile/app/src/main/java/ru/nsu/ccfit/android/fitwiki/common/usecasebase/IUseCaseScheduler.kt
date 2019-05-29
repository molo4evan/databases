package ru.nsu.ccfit.android.fitwiki.common.usecasebase

interface IUseCaseScheduler {
    fun exectue(runnable: Runnable)

    fun <T: UseCase.ResponseValues> onResponce(responce: T, callback: IUseCaseCallback<T>)

    fun <T: UseCase.ResponseValues> onError(callback: IUseCaseCallback<T>)
}