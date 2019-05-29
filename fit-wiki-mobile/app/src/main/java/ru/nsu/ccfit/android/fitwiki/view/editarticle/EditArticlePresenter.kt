package ru.nsu.ccfit.android.fitwiki.view.editarticle

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.IUseCaseCallback
import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCaseHandler
import ru.nsu.ccfit.android.fitwiki.common.usecases.articles.EditArticle
import ru.nsu.ccfit.android.fitwiki.common.usecases.articles.LoadArticle
import ru.nsu.ccfit.android.fitwiki.common.usecases.articles.LoadOfferedArticle
import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController
import ru.nsu.ccfit.android.fitwiki.model.Article
import java.lang.Exception

class EditArticlePresenter(
        private val view: IEditArticleView,
        private val articleID: String,
        private val sectionName: String
): IEditArticlePresenter {
    private lateinit var article: Article
    private lateinit var aCtrl: IArticleController
    private lateinit var uCtrl: IUserController

    override fun editArticle(newState: Article) {
        val edit = EditArticle(aCtrl)
        UseCaseHandler.instance.execute(
                edit,
                EditArticle.RequestValues(article),
                object : IUseCaseCallback<EditArticle.ResponseValues>{
                    override fun onSuccess(response: EditArticle.ResponseValues) {
                        view.dispose()
                    }

                    override fun onError() {
                        view.dispose()  //TODO: implement showError
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
        val load = LoadArticle(aCtrl, uCtrl)
        UseCaseHandler.instance.execute(
                load,
                LoadArticle.RequestValues(articleID, sectionName),
                object : IUseCaseCallback<LoadArticle.ResponseValues>{
                    override fun onSuccess(response: LoadArticle.ResponseValues) {
                        view.showInfo(response.article, response.user.username)
                    }

                    override fun onError() {
                        view.dispose()  //TODO: implement showError
                    }

                }
        )

    }

}