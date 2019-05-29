package ru.nsu.ccfit.android.fitwiki.view.createarticle

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.IUseCaseCallback
import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCaseHandler
import ru.nsu.ccfit.android.fitwiki.common.usecases.articles.CreateArticle
import ru.nsu.ccfit.android.fitwiki.common.usecases.articles.LoadSections
import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import java.lang.Exception

class CreateArticlePresenter(private val view: ICreateArticleView): ICreateArticlePresenter {
    private lateinit var ctrl: IArticleController

    override fun createArticle(
            articleTitle: String,
            articleText: String,
            sectionName: String,
            articleSummary: String?
    ) {
        val create = CreateArticle(ctrl)
        val request = CreateArticle.RequestValues(
                articleTitle,
                articleText,
                null,
                sectionName,
                articleSummary
        )
        UseCaseHandler.instance.execute(
                create,
                request,
                object : IUseCaseCallback<CreateArticle.ResponseValues>{
                    override fun onSuccess(response: CreateArticle.ResponseValues) {
                        view.dispose()
                    }

                    override fun onError() {
                        //view.showError()
                    }
                }
        )
    }

    override fun setControllers(controllers: Map<IController.ControllerType, IController>) {
        ctrl = controllers[IController.ControllerType.ARTICLES] as? IArticleController
                ?: throw Exception()
    }

    override fun start() {
        val load = LoadSections(ctrl)
        UseCaseHandler.instance.execute(
                load,
                LoadSections.RequestValues(),
                object : IUseCaseCallback<LoadSections.ResponseValues>{
                    override fun onSuccess(response: LoadSections.ResponseValues) {
                        view.setUpSections(response.sections)
                    }

                    override fun onError() {
                        view.dispose()  //TODO: implement showError
                    }

                }
        )
    }
}