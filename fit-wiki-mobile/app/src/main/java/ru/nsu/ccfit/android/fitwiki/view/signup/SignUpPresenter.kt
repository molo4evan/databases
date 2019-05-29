package ru.nsu.ccfit.android.fitwiki.view.signup

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.IUseCaseCallback
import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCaseHandler
import ru.nsu.ccfit.android.fitwiki.common.usecases.users.SignIn
import ru.nsu.ccfit.android.fitwiki.common.usecases.users.SignUp
import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController
import java.net.URL

class SignUpPresenter(private val view: ISignUpView): ISignUpPresenter {
    private lateinit var controllers: Map<IController.ControllerType, IController>

    override fun onRegister(username: String, password: String, photoUrl: String?) {
        val url = try {
            if (photoUrl != null) URL(photoUrl) else null
        } catch (ex: Exception){
            view.returnStatus(false)
            return
        }
        val userController = controllers[IController.ControllerType.USERS] as? IUserController
                ?: throw Exception()
        val load = SignUp(userController)
        UseCaseHandler.instance.execute(
                load,
                SignUp.RequestValues(username, password, url),
                object : IUseCaseCallback<SignUp.ResponseValues> {
                    override fun onSuccess(response: SignUp.ResponseValues) {
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