package ru.nsu.ccfit.android.fitwiki.view.editarticle

import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.view.base.IBaseView

interface IEditArticleView: IBaseView<IEditArticlePresenter> {
    fun showInfo(article: Article, authorName: String)

    fun dispose()
}