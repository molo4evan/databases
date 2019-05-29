package ru.nsu.ccfit.android.fitwiki.view.navigation

import ru.nsu.ccfit.android.fitwiki.model.UserInfo
import ru.nsu.ccfit.android.fitwiki.view.base.IBaseView

interface IMenuView: IBaseView<IMenuPresenter> {
    fun updateUI(user: UserInfo?)
}