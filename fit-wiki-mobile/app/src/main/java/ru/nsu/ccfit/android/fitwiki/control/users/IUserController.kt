package ru.nsu.ccfit.android.fitwiki.control.users

import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.model.UserInfo
import java.net.URL

interface IUserController: IController {
    interface ILoadUsersCallback {
        fun onUsersLoaded(users: List<UserInfo>)
    }

    interface ILoadUserByIDCallback {
        fun onUserLoaded(user: UserInfo?)
    }

    interface ILoadUserByNameCallback {
        fun onUserLoaded(user: UserInfo?)
    }

    interface ILoadCurrentUserCallback {
        fun onUserLoaded(user: UserInfo?)
    }

    interface IRegisterCallback {
        fun onRegister(success: Boolean)
    }

    interface ILoginCallback {
        fun onLogin(success: Boolean)
    }

    interface ILogoutCallBack {
        fun onLogout()
    }

    interface ISetBannedCallback {
        fun onBannedSet()
    }

    interface ISetModeratorCallback {
        fun onModeratorSet()
    }

    fun loadUsers(callback: ILoadUsersCallback)

    fun loadUserByID(userID: String, callback: ILoadUserByIDCallback)

    fun loadUserByName(username: String, callback: ILoadUserByNameCallback)

    fun loadCurrentUser(callback: ILoadCurrentUserCallback)

    fun register(username: String, password: String, photoURL: URL?, callback: IRegisterCallback)

    fun login(username: String, password: String, callback: ILoginCallback)

    fun logout(callback: ILogoutCallBack)

    fun loadOrCreateCurrentUser(callback: ILoadCurrentUserCallback)

    fun setBanned(userID: String, ban: Boolean, callback: ISetBannedCallback)

    fun setModerator(userID: String, moder: Boolean, callback: ISetModeratorCallback)
}