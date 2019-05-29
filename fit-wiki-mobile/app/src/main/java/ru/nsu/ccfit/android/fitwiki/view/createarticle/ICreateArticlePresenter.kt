package ru.nsu.ccfit.android.fitwiki.view.createarticle

import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.view.base.IBasePresenter

interface ICreateArticlePresenter: IBasePresenter {
    fun createArticle(
            articleTitle: String,
            articleText: String,
            sectionName: String,
            articleSummary: String?
    )
}