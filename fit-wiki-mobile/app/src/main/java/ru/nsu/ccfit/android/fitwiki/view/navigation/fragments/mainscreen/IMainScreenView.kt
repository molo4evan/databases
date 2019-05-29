package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.mainscreen

import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.view.base.IBaseView

interface IMainScreenView: IBaseView<IMainScreenPresenter> {
    fun showRecent(articles: List<Article>)

    fun openArticleDetaiedScreeen(articleID: String, sectionName: String)
}