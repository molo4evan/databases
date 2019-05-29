package ru.nsu.ccfit.android.fitwiki.view.base

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.IUseCaseCallback
import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCaseHandler
import ru.nsu.ccfit.android.fitwiki.common.usecases.users.LoadCurrentUser
import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController

abstract class LoadUserPresenter: IBasePresenter {
    private lateinit var userController: IUserController

    override fun setControllers(controllers: Map<IController.ControllerType, IController>) {
        userController = controllers[IController.ControllerType.USERS] as? IUserController
                ?: throw Exception()
    }

    fun loadCurrentUserInfo(callback: IUseCaseCallback<LoadCurrentUser.ResponseValues>){
        val load = LoadCurrentUser(userController)
        UseCaseHandler.instance.execute(
                load,
                LoadCurrentUser.RequestValues(),
                callback
        )
    }
}