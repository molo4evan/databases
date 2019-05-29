package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.offered

import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.view.base.IBasePresenter

interface IOfferedPresenter: IBasePresenter {
    fun onArticleSelected(article: Article)
}