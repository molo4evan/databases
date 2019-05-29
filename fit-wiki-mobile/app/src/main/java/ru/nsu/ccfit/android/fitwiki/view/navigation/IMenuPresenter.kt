package ru.nsu.ccfit.android.fitwiki.view.navigation

import ru.nsu.ccfit.android.fitwiki.view.base.IBasePresenter

interface IMenuPresenter: IBasePresenter {
    fun loadCurrentUser()

    fun logoutCurrentUser()
}