package ru.nsu.ccfit.android.fitwiki.common.usecases.users

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController
import ru.nsu.ccfit.android.fitwiki.model.UserInfo

class LoadUsers(private val controller: IUserController): UseCase<LoadUsers.RequestValues, LoadUsers.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        controller.loadUsers(object: IUserController.ILoadUsersCallback {
            override fun onUsersLoaded(users: List<UserInfo>) {
                val response = ResponseValues(users)
                useCaseCallback.onSuccess(response)
            }

        })
    }

    class RequestValues: UseCase.RequestValues

    class ResponseValues(val users: List<UserInfo>): UseCase.ResponseValues
}