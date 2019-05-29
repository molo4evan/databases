package ru.nsu.ccfit.android.fitwiki.view.navigation

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.IUseCaseCallback
import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCaseHandler
import ru.nsu.ccfit.android.fitwiki.common.usecases.users.LoadCurrentUser
import ru.nsu.ccfit.android.fitwiki.common.usecases.users.LogoutCurrentUser
import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController
import java.lang.Exception

class MenuPresenter(private val view: IMenuView): IMenuPresenter {
    private lateinit var controllers: Map<IController.ControllerType, IController>

    override fun loadCurrentUser() {
        val uCtrl = controllers[IController.ControllerType.USERS] as? IUserController ?:
        throw Exception()   //TODO: stub

        val load = LoadCurrentUser(uCtrl)
        UseCaseHandler.instance.execute(
                load,
                LoadCurrentUser.RequestValues(),
                object : IUseCaseCallback<LoadCurrentUser.ResponseValues> {
                    override fun onSuccess(response: LoadCurrentUser.ResponseValues) {
                        view.updateUI(response.user)
                    }

                    override fun onError() {
                        view.updateUI(null)
                    }
                }
        )
    }

    override fun logoutCurrentUser(){
        val uCtrl = controllers[IController.ControllerType.USERS] as? IUserController ?:
        throw Exception()   //TODO: stub

        val logout = LogoutCurrentUser(uCtrl)
        UseCaseHandler.instance.execute(
                logout,
                LogoutCurrentUser.RequestValues(),
                object : IUseCaseCallback<LogoutCurrentUser.ResponseValues> {
                    override fun onSuccess(response: LogoutCurrentUser.ResponseValues) {
                        view.updateUI(null)
                    }

                    override fun onError() {
                        view.updateUI(null)
                    }
                }
        )
    }

    override fun setControllers(controllers: Map<IController.ControllerType, IController>) {
        this.controllers = controllers
    }

    override fun start() {
        loadCurrentUser()
    }
}