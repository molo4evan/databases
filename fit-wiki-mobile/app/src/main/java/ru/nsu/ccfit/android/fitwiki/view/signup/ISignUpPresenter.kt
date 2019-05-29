package ru.nsu.ccfit.android.fitwiki.view.signup

import ru.nsu.ccfit.android.fitwiki.view.base.IBasePresenter

interface ISignUpPresenter: IBasePresenter {
    fun onRegister(username: String, password: String, photoUrl: String?)
}