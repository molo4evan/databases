package ru.nsu.ccfit.android.fitwiki.view.viewarticle

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.IUseCaseCallback
import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCaseHandler
import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController
import ru.nsu.ccfit.android.fitwiki.common.usecases.articles.LoadArticle
import ru.nsu.ccfit.android.fitwiki.common.usecases.articles.UpdateArticleRating
import ru.nsu.ccfit.android.fitwiki.common.usecases.users.LoadCurrentUser
import ru.nsu.ccfit.android.fitwiki.view.base.LoadUserPresenter
import java.lang.Exception

class ViewArticlePresenter(
        private val view: IViewArticleView,
        private val articleID: String,
        private val sectionName: String
): IViewArticlePresenter, LoadUserPresenter() {
    private lateinit var controllers: Map<IController.ControllerType, IController>

    //region IViewArticlePresenter
    override fun setControllers(controllers: Map<IController.ControllerType, IController>) {
        super.setControllers(controllers)
        this.controllers = controllers
    }

    override fun start() {
        loadArticle()
        loadCurrentUser()
    }

    override fun onUserProfileSelected() {
        view.openUserProfile()
    }

    override fun onDecreaseArticleRating() {
        val aControl = controllers[IController.ControllerType.ARTICLES] as? IArticleController
                ?: throw Exception()    //TODO: stub
        val increase = UpdateArticleRating(aControl)
        UseCaseHandler.instance.execute(increase, UpdateArticleRating.RequestValues(articleID, false),
                object : IUseCaseCallback<UpdateArticleRating.ResponseValues>{
                    override fun onSuccess(response: UpdateArticleRating.ResponseValues) {
                        loadArticle()
                        loadCurrentUser()
                    }

                    override fun onError() {
                    }
                })
    }

    override fun onIncreaseArticleRating() {
        val aControl = controllers[IController.ControllerType.ARTICLES] as? IArticleController
                ?: throw Exception()    //TODO: stub
        val decrease = UpdateArticleRating(aControl)
        UseCaseHandler.instance.execute(decrease, UpdateArticleRating.RequestValues(articleID, true),
                object : IUseCaseCallback<UpdateArticleRating.ResponseValues>{
                    override fun onSuccess(response: UpdateArticleRating.ResponseValues) {
                        loadArticle()
                        loadCurrentUser()
                    }

                    override fun onError() {
                    }
                })
    }
    //endregion

    private fun loadArticle() {
        val aControl = controllers[IController.ControllerType.ARTICLES] as? IArticleController
                ?: throw Exception()    //TODO: stub
        val uControl = controllers[IController.ControllerType.USERS] as? IUserController
                ?: throw Exception()    //TODO: stub
        val load = LoadArticle(aControl, uControl)

        UseCaseHandler.instance.execute(load, LoadArticle.RequestValues(articleID, sectionName),
                object: IUseCaseCallback<LoadArticle.ResponseValues> {
                    override fun onSuccess(response: LoadArticle.ResponseValues) {
                        view.showArticle(response.article, response.user)
                    }

                    override fun onError() {
                        view.showErrorScreen()
                    }
                })
    }

    private fun loadCurrentUser(){
        super.loadCurrentUserInfo(object : IUseCaseCallback<LoadCurrentUser.ResponseValues>{
            override fun onSuccess(response: LoadCurrentUser.ResponseValues) {
                view.setupSettings(response.user)
            }

            override fun onError() {
                view.setupSettings(null)
            }

        })
    }
}