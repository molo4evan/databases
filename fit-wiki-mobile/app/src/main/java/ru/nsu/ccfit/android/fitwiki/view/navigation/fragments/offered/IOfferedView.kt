package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.offered

import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.view.base.IBaseView

interface IOfferedView: IBaseView<IOfferedPresenter> {
    fun showOffered(articles: List<Article>)

    fun openOfferedArticle(articleID: String)
}