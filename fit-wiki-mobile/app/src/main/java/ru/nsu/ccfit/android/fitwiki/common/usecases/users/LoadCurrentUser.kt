package ru.nsu.ccfit.android.fitwiki.common.usecases.users

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController
import ru.nsu.ccfit.android.fitwiki.model.UserInfo

class LoadCurrentUser(private val uCtrl: IUserController):
        UseCase<LoadCurrentUser.RequestValues, LoadCurrentUser.ResponseValues>(){
    override fun executeUseCase(requestValues: RequestValues) {
        uCtrl.loadCurrentUser(object : IUserController.ILoadCurrentUserCallback {
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