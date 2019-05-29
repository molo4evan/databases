package ru.nsu.ccfit.android.fitwiki.view.viewmyprofile

import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.model.UserInfo
import ru.nsu.ccfit.android.fitwiki.view.base.IBaseView

interface IViewMyProfileView: IBaseView<IViewMyProfilePresenter> {
    fun showProfile(user: UserInfo, articles: List<Article>)

    fun showErrorScreen()

    fun openArticle(article: Article)
}