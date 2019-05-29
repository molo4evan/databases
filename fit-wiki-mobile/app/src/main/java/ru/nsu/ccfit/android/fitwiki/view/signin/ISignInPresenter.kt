package ru.nsu.ccfit.android.fitwiki.view.signin

import ru.nsu.ccfit.android.fitwiki.view.base.IBasePresenter

interface ISignInPresenter: IBasePresenter {
    fun onLogin(username: String, password: String)
}