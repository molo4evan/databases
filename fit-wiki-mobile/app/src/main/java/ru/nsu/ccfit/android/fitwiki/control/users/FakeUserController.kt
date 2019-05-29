package ru.nsu.ccfit.android.fitwiki.control.users

import ru.nsu.ccfit.android.fitwiki.common.netstub.FakeUsersRepo
import ru.nsu.ccfit.android.fitwiki.model.UserInfo
import java.net.URL

class FakeUserController: IUserController {
    override fun register(username: String, password: String, photoURL: URL?, callback: IUserController.IRegisterCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun login(username: String, password: String, callback: IUserController.ILoginCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun logout(callback: IUserController.ILogoutCallBack) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadOrCreateCurrentUser(callback: IUserController.ILoadCurrentUserCallback) {
        callback.onUserLoaded( UserInfo("3", "Бог всея приложения", isAdmin = true, rating = 9999))
    }

    private val users = FakeUsersRepo.users.values.toList()

    override fun loadUsers(callback: IUserController.ILoadUsersCallback) {
        callback.onUsersLoaded(users)
    }

    override fun loadUserByID(userID: String, callback: IUserController.ILoadUserByIDCallback) {
        val user = FakeUsersRepo.users[userID]
        callback.onUserLoaded(user)
    }

    override fun loadUserByName(username: String, callback: IUserController.ILoadUserByNameCallback) {
        for (user in FakeUsersRepo.users.values) {
            if (user.username == username) {
                callback.onUserLoaded(user)
                return
            }
        }
        callback.onUserLoaded(null)
    }

    override fun loadCurrentUser(callback: IUserController.ILoadCurrentUserCallback) {
        callback.onUserLoaded( UserInfo("3", "Бог всея приложения", isAdmin = true, rating = 9999))
    }

    override fun setBanned(userID: String, ban: Boolean, callback: IUserController.ISetBannedCallback) {
        callback.onBannedSet()
    }

    override fun setModerator(userID: String, moder: Boolean, callback: IUserController.ISetModeratorCallback) {
        callback.onModeratorSet()
    }
}