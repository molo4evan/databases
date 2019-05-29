package ru.nsu.ccfit.android.fitwiki.view.viewarticle

import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.model.UserInfo
import ru.nsu.ccfit.android.fitwiki.view.base.IBaseView

interface IViewArticleView: IBaseView<IViewArticlePresenter> {
    fun showArticle(article: Article, user: UserInfo)

    fun showErrorScreen()

    fun openUserProfile()

    fun setupSettings(user: UserInfo?)
}