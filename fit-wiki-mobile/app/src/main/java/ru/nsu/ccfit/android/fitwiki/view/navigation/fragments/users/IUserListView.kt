package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.users

import ru.nsu.ccfit.android.fitwiki.model.UserInfo
import ru.nsu.ccfit.android.fitwiki.view.base.IBaseView

interface IUserListView: IBaseView<IUserListPresenter> {
    fun showUsers(users: List<UserInfo>)

    fun openUserProfile(userID: String)
}