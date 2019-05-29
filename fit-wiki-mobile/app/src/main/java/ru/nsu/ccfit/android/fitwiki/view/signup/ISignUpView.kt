package ru.nsu.ccfit.android.fitwiki.view.signup

import ru.nsu.ccfit.android.fitwiki.view.base.IBaseView

interface ISignUpView: IBaseView<ISignUpPresenter> {
    fun returnStatus(success: Boolean)
}