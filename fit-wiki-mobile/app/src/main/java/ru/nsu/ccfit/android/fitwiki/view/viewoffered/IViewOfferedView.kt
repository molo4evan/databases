package ru.nsu.ccfit.android.fitwiki.view.viewoffered

import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.view.base.IBaseView

interface IViewOfferedView: IBaseView<IViewOfferedPresenter> {
    fun showArticle(article: Article, authorName: String)

    fun dispose()
}