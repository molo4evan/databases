package ru.nsu.ccfit.android.fitwiki.view.viewsection

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.IUseCaseCallback
import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCaseHandler
import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.common.usecases.articles.LoadSection
import java.lang.Exception

class ViewSectionPresenter(private val view: IViewSectionView, private val sectionName: String): IViewSectionPresenter {
    private lateinit var controllers: Map<IController.ControllerType, IController>

    //region IViewSectionPresenter
    override fun setControllers(controllers: Map<IController.ControllerType, IController>) {
        this.controllers = controllers
    }

    override fun start() {
        loadSection()
    }

    override fun onArticleSelected(article: Article) {
        view.openArticle(article.id.toString(), article.sectionName)
    }
    //endregion

    private fun loadSection(){
        val aControl = controllers[IController.ControllerType.ARTICLES] as? IArticleController
                ?: throw Exception()    //TODO: stub
        val load = LoadSection(aControl)
        UseCaseHandler.instance.execute(load, LoadSection.RequestValues(sectionName),
                object : IUseCaseCallback<LoadSection.ResponseValues> {
                    override fun onSuccess(response: LoadSection.ResponseValues) {
                        view.showSection(response.articles)
                    }

                    override fun onError() {
                        view.showErrorScreen()
                    }
                })
    }
}