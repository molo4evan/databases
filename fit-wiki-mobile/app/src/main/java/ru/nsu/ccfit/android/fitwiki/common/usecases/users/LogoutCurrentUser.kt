package ru.nsu.ccfit.android.fitwiki.common.usecases.users

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController

class LogoutCurrentUser(private val uCtrl: IUserController):
        UseCase<LogoutCurrentUser.RequestValues, LogoutCurrentUser.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        uCtrl.logout(object : IUserController.ILogoutCallBack {
            override fun onLogout() {
                useCaseCallback.onSuccess(ResponseValues())
            }
        })
    }

    class RequestValues: UseCase.RequestValues
    class ResponseValues: UseCase.ResponseValues
}