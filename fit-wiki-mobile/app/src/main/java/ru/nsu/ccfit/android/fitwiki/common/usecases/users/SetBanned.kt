package ru.nsu.ccfit.android.fitwiki.common.usecases.users

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController

class SetBanned(private val uCtrl: IUserController):
        UseCase<SetBanned.RequestValues, SetBanned.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        uCtrl.setBanned(requestValues.userID, requestValues.ban, object : IUserController.ISetBannedCallback {
            override fun onBannedSet() {
                useCaseCallback.onSuccess(ResponseValues())
            }
        })
    }

    class RequestValues(val userID: String, val ban: Boolean): UseCase.RequestValues

    class ResponseValues: UseCase.ResponseValues
}