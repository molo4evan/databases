package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.mainscreen

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.IUseCaseCallback
import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCaseHandler
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.common.usecases.articles.LoadRecent
import java.lang.Exception

class MainScreenPresenter(private val view: IMainScreenView): IMainScreenPresenter {
    private lateinit var controllers: Map<IController.ControllerType, IController>

    override fun setControllers(controllers: Map<IController.ControllerType, IController>) {
        this.controllers = controllers
    }

    //region IMainScreenPresenter
    override fun start() {
        loadRecentArticles()
    }

    override fun onArticleChosen(article: Article) {
        view.openArticleDetaiedScreeen(article.id.toString(), article.sectionName)
    }
    //endregion

    private fun loadRecentArticles(){
        val articleControl = controllers[IController.ControllerType.ARTICLES] as? IArticleController
                ?: throw Exception()    //TODO: stub
        val load = LoadRecent(articleControl)
        UseCaseHandler.instance.execute(load, LoadRecent.RequestValues(), object : IUseCaseCallback<LoadRecent.ResponseValues> {
            override fun onSuccess(response: LoadRecent.ResponseValues) {
                val recent = response.articles
                processRecent(recent)
            }

            override fun onError() {
            }
        })
    }

    private fun processRecent(articles: List<Article>){
        view.showRecent(articles)
    }
}