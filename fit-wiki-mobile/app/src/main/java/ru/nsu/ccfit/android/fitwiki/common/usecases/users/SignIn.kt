package ru.nsu.ccfit.android.fitwiki.common.usecases.users

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController

class SignIn(private val uCtrl: IUserController):
        UseCase<SignIn.RequestValues, SignIn.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        uCtrl.login(requestValues.username, requestValues.password, object : IUserController.ILoginCallback {
            override fun onLogin(success: Boolean) {
                if (success){
                    useCaseCallback.onSuccess(ResponseValues())
                } else {
                    useCaseCallback.onError()
                }
            }
        })
    }

    class RequestValues(val username: String, val password: String): UseCase.RequestValues
    class ResponseValues(): UseCase.ResponseValues
}