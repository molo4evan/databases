package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.offered

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.IUseCaseCallback
import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCaseHandler
import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.common.usecases.articles.LoadOffered
import java.lang.Exception

class OfferedPresenter(private val view: IOfferedView): IOfferedPresenter {
    private lateinit var controllers: Map<IController.ControllerType, IController>

    override fun setControllers(controllers: Map<IController.ControllerType, IController>) {
        this.controllers = controllers
    }

    override fun onArticleSelected(article: Article) {
        view.openOfferedArticle(article.id.toString())
    }

    override fun start() {
        loadOffered()
    }

    private fun loadOffered(){
        val articleControl = controllers[IController.ControllerType.ARTICLES] as? IArticleController
                ?: throw Exception()    //TODO: stub
        val load = LoadOffered(articleControl)
        UseCaseHandler.instance.execute(load, LoadOffered.RequestValues(), object : IUseCaseCallback<LoadOffered.ResponseValues> {
            override fun onSuccess(response: LoadOffered.ResponseValues) {
                val offered = response.articles
                view.showOffered(offered)
            }

            override fun onError() {
            }
        })
    }
}