package ru.nsu.ccfit.android.fitwiki.view.signin

import ru.nsu.ccfit.android.fitwiki.view.base.IBaseView

interface ISignInView: IBaseView<ISignInPresenter> {
    fun returnStatus(success: Boolean)
}