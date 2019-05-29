package ru.nsu.ccfit.android.fitwiki.view.viewuserprofile

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.IUseCaseCallback
import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCaseHandler
import ru.nsu.ccfit.android.fitwiki.common.usecases.users.LoadCurrentUser
import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController
import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.common.usecases.users.LoadUserByID
import ru.nsu.ccfit.android.fitwiki.common.usecases.users.SetBanned
import ru.nsu.ccfit.android.fitwiki.common.usecases.users.SetModerator
import ru.nsu.ccfit.android.fitwiki.view.base.LoadUserPresenter
import java.lang.Exception

class ViewUserProfilePresenter(
        private val view: IViewUserProfileView,
        private val userID: String
): IViewUserProfilePresenter, LoadUserPresenter() {
    private lateinit var controllers: Map<IController.ControllerType, IController>

    override fun onArticleSelected(article: Article) {
        view.openArticle(article)
    }

    override fun onSetBanned(state: Boolean) {
        val uControl = controllers[IController.ControllerType.USERS] as? IUserController
                ?: throw Exception()    //TODO: stub
        val ban = SetBanned(uControl)
        UseCaseHandler.instance.execute(ban, SetBanned.RequestValues(userID, state),
                object : IUseCaseCallback<SetBanned.ResponseValues>{
                    override fun onSuccess(response: SetBanned.ResponseValues) {
                        loadUser()
                        loadMyself()
                    }

                    override fun onError() {
                        if (state){
                            view.cannotBan()
                        }
                    }
                })
    }

    override fun onSetModerator(state: Boolean) {
        val uControl = controllers[IController.ControllerType.USERS] as? IUserController
                ?: throw Exception()    //TODO: stub
        val moder = SetModerator(uControl)
        UseCaseHandler.instance.execute(moder, SetModerator.RequestValues(userID, state),
                object : IUseCaseCallback<SetModerator.ResponseValues>{
                    override fun onSuccess(response: SetModerator.ResponseValues) {
                        loadUser()
                        loadMyself()
                    }

                    override fun onError() {
                    }
                })
    }

    override fun setControllers(controllers: Map<IController.ControllerType, IController>) {
        super.setControllers(controllers)
        this.controllers = controllers
    }

    override fun start() {
        loadUser()
        loadMyself()
    }

    override fun loadUser(){
        val aControl = controllers[IController.ControllerType.ARTICLES] as? IArticleController
                ?: throw Exception()    //TODO: stub
        val uControl = controllers[IController.ControllerType.USERS] as? IUserController
                ?: throw Exception()    //TODO: stub
        val load = LoadUserByID(aControl, uControl)
        UseCaseHandler.instance.execute(load, LoadUserByID.RequestValues(userID),
                object : IUseCaseCallback<LoadUserByID.ResponseValues> {
                    override fun onSuccess(response: LoadUserByID.ResponseValues) {
                        view.showProfile(response.user, response.articles)
                    }

                    override fun onError() {
                        view.showErrorScreen()
                    }

                })
    }

    override fun loadMyself(){
        super.loadCurrentUserInfo(object: IUseCaseCallback<LoadCurrentUser.ResponseValues>{
            override fun onSuccess(response: LoadCurrentUser.ResponseValues) {
                view.updateMyPermission(response.user)
            }

            override fun onError() {
                view.updateMyPermission(null)
            }

        })
    }
}