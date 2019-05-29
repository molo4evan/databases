package ru.nsu.ccfit.android.fitwiki.common.usecases.users

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController
import ru.nsu.ccfit.android.fitwiki.model.UserInfo

class LoadOrCreateCurrentUser(private val uCtrl: IUserController):
        UseCase<LoadOrCreateCurrentUser.RequestValues, LoadOrCreateCurrentUser.ResponseValues>(){
    override fun executeUseCase(requestValues: RequestValues) {
        uCtrl.loadOrCreateCurrentUser(object : IUserController.ILoadCurrentUserCallback {
            override fun onUserLoaded(user: UserInfo?) {
                if (user == null){
                    useCaseCallback.onError()
                } else {
                    useCaseCallback.onSuccess(ResponseValues(user))
                }
            }
        })
    }

    class RequestValues: UseCase.RequestValues

    class ResponseValues(val user: UserInfo): UseCase.ResponseValues
}