package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.users

import ru.nsu.ccfit.android.fitwiki.model.UserInfo
import ru.nsu.ccfit.android.fitwiki.view.base.IBasePresenter

interface IUserListPresenter: IBasePresenter {
    fun onUserSelected(user: UserInfo)
}