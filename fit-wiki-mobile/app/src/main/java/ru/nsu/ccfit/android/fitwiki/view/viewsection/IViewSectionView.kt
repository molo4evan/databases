package ru.nsu.ccfit.android.fitwiki.view.viewsection

import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.view.base.IBaseView

interface IViewSectionView: IBaseView<IViewSectionPresenter> {
    fun showSection(section: List<Article>)

    fun showErrorScreen()

    fun openArticle(articleID: String, sectionName: String)
}