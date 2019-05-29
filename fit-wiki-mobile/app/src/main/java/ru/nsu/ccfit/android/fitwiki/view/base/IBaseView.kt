package ru.nsu.ccfit.android.fitwiki.view.base

interface IBaseView<in T: IBasePresenter> {
    fun setPresenter(presenter: T)
}