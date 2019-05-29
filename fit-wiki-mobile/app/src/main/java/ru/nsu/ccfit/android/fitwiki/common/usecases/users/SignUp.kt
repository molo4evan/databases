package ru.nsu.ccfit.android.fitwiki.common.usecases.users

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController
import java.net.URL

class SignUp(private val uCtrl: IUserController):
        UseCase<SignUp.RequestValues, SignUp.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        uCtrl.register(requestValues.username, requestValues.password, requestValues.photoUrl,
                object : IUserController.IRegisterCallback {
                    override fun onRegister(success: Boolean) {
                        if (success){
                            useCaseCallback.onSuccess(ResponseValues())
                        } else {
                            useCaseCallback.onError()
                        }
                    }
                })
    }

    class RequestValues(val username: String, val password: String, val photoUrl: URL?): UseCase.RequestValues
    class ResponseValues: UseCase.ResponseValues
}