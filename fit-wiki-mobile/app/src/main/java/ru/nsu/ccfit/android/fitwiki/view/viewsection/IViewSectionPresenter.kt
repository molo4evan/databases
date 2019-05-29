package ru.nsu.ccfit.android.fitwiki.view.viewsection

import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.view.base.IBasePresenter

interface IViewSectionPresenter: IBasePresenter {
    fun onArticleSelected(article: Article)
}