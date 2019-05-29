package ru.nsu.ccfit.android.fitwiki.view.viewoffered

import ru.nsu.ccfit.android.fitwiki.view.base.IBasePresenter

interface IViewOfferedPresenter: IBasePresenter {
    fun confirm()

    fun cancel()
}