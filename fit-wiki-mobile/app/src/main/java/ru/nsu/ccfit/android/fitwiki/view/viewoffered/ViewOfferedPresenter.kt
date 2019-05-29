package ru.nsu.ccfit.android.fitwiki.view.viewoffered

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.IUseCaseCallback
import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCaseHandler
import ru.nsu.ccfit.android.fitwiki.common.usecases.articles.CancelArticle
import ru.nsu.ccfit.android.fitwiki.common.usecases.articles.LoadOfferedArticle
import ru.nsu.ccfit.android.fitwiki.common.usecases.articles.PublishArticle
import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController
import java.lang.Exception

class ViewOfferedPresenter(private val view: IViewOfferedView, private val articleID: String): IViewOfferedPresenter {
    private lateinit var aCtrl: IArticleController
    private lateinit var uCtrl: IUserController

    override fun confirm() {
        val confirm = PublishArticle(aCtrl)
        UseCaseHandler.instance.execute(
                confirm,
                PublishArticle.RequestValues(articleID),
                object : IUseCaseCallback<PublishArticle.ResponseValues>{
                    override fun onSuccess(response: PublishArticle.ResponseValues) {
                        view.dispose()
                    }

                    override fun onError() {
                        //view.showError()
                    }
                }
        )
    }

    override fun cancel() {
        val confirm = CancelArticle(aCtrl)
        UseCaseHandler.instance.execute(
                confirm,
                CancelArticle.RequestValues(articleID),
                object : IUseCaseCallback<CancelArticle.ResponseValues>{
                    override fun onSuccess(response: CancelArticle.ResponseValues) {
                        view.dispose()
                    }

                    override fun onError() {
                        //view.showError()
                    }
                }
        )
    }

    override fun setControllers(controllers: Map<IController.ControllerType, IController>) {
        aCtrl = controllers[IController.ControllerType.ARTICLES] as? IArticleController
                ?: throw Exception()
        uCtrl = controllers[IController.ControllerType.USERS] as? IUserController
                ?: throw Exception()
    }

    override fun start() {
        val load = LoadOfferedArticle(aCtrl, uCtrl)
        UseCaseHandler.instance.execute(
                load,
                LoadOfferedArticle.RequestValues(articleID),
                object : IUseCaseCallback<LoadOfferedArticle.ResponseValues>{
                    override fun onSuccess(response: LoadOfferedArticle.ResponseValues) {
                        view.showArticle(response.article, response.user.username)
                    }

                    override fun onError() {
                        //view.showError()
                    }
                }
        )
    }
}