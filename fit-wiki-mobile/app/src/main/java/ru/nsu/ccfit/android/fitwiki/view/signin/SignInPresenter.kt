package ru.nsu.ccfit.android.fitwiki.view.signin

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.IUseCaseCallback
import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCaseHandler
import ru.nsu.ccfit.android.fitwiki.common.usecases.users.SignIn
import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController

class SignInPresenter(private val view: ISignInView): ISignInPresenter {
    private lateinit var controllers: Map<IController.ControllerType, IController>

    override fun onLogin(username: String, password: String) {
        val userController = controllers[IController.ControllerType.USERS] as? IUserController
                ?: throw Exception()
        val load = SignIn(userController)
        UseCaseHandler.instance.execute(
                load,
                SignIn.RequestValues(username, password),
                object : IUseCaseCallback<SignIn.ResponseValues> {
                    override fun onSuccess(response: SignIn.ResponseValues) {
                        view.returnStatus(true)
                    }

                    override fun onError() {
                        view.returnStatus(false)
                    }
                }
        )
    }

    override fun setControllers(controllers: Map<IController.ControllerType, IController>) {
        this.controllers = controllers
    }

    override fun start() {
    }
}