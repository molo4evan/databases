package ru.nsu.ccfit.android.fitwiki.common.usecases.users

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController

class SetModerator(private val uCtrl: IUserController):
        UseCase<SetModerator.RequestValues, SetModerator.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        uCtrl.setModerator(requestValues.userID, requestValues.moder, object : IUserController.ISetModeratorCallback {
            override fun onModeratorSet() {
                useCaseCallback.onSuccess(ResponseValues())
            }
        })
    }

    class RequestValues(val userID: String, val moder: Boolean): UseCase.RequestValues

    class ResponseValues: UseCase.ResponseValues
}