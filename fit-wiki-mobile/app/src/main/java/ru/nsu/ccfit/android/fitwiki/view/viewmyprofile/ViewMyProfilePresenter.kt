package ru.nsu.ccfit.android.fitwiki.view.viewmyprofile

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.IUseCaseCallback
import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCaseHandler
import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController
import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.common.usecases.users.LoadCurrentUserWithArticles
import java.lang.Exception

class ViewMyProfilePresenter(                 //TODO: change to load by id?
        private val view: IViewMyProfileView
): IViewMyProfilePresenter {
    private lateinit var controllers: Map<IController.ControllerType, IController>

    override fun onArticleSelected(article: Article) {
        view.openArticle(article)
    }

    override fun setControllers(controllers: Map<IController.ControllerType, IController>) {
        this.controllers = controllers
    }

    override fun start() {
        loadUser()
    }

    override fun loadUser(){
        val aControl = controllers[IController.ControllerType.ARTICLES] as? IArticleController
                ?: throw Exception()    //TODO: stub
        val uControl = controllers[IController.ControllerType.USERS] as? IUserController
                ?: throw Exception()    //TODO: stub

        val load = LoadCurrentUserWithArticles(uControl, aControl)
        UseCaseHandler.instance.execute(load, LoadCurrentUserWithArticles.RequestValues(),
                object : IUseCaseCallback<LoadCurrentUserWithArticles.ResponseValues> {
                    override fun onSuccess(response: LoadCurrentUserWithArticles.ResponseValues) {
                        view.showProfile(response.user, response.articles)
                    }

                    override fun onError() {
                        view.showErrorScreen()
                    }

                })
    }
}