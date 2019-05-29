package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.users

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.IUseCaseCallback
import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCaseHandler
import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController
import ru.nsu.ccfit.android.fitwiki.model.UserInfo
import ru.nsu.ccfit.android.fitwiki.common.usecases.users.LoadUsers
import java.lang.Exception

class UserListPresenter(private val view: IUserListView): IUserListPresenter {
    private lateinit var controllers: Map<IController.ControllerType, IController>

    override fun setControllers(controllers: Map<IController.ControllerType, IController>) {
        this.controllers = controllers
    }

    override fun onUserSelected(user: UserInfo) {
        view.openUserProfile(user.id)
    }

    override fun start() {
        loadUsers()
    }

    private fun loadUsers() {
        val uControl = controllers[IController.ControllerType.USERS] as? IUserController
                ?: throw Exception()    //TODO: stub
        val load = LoadUsers(uControl)
        UseCaseHandler.instance.execute(load, LoadUsers.RequestValues(),
                object : IUseCaseCallback<LoadUsers.ResponseValues> {
            override fun onSuccess(response: LoadUsers.ResponseValues) {
                view.showUsers(response.users)
            }

            override fun onError() {
            }
        })
    }


}