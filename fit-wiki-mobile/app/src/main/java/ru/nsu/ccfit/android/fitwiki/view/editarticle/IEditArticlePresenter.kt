package ru.nsu.ccfit.android.fitwiki.view.editarticle

import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.view.base.IBasePresenter

interface IEditArticlePresenter: IBasePresenter {
    fun editArticle(newState: Article)
}