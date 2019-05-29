package ru.nsu.ccfit.android.fitwiki.common.usecasebase

interface IUseCaseCallback<T> {
    fun onSuccess(response: T)
    fun onError()
}