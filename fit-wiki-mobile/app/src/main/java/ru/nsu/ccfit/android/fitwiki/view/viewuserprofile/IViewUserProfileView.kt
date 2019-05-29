package ru.nsu.ccfit.android.fitwiki.view.viewuserprofile

import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.model.UserInfo
import ru.nsu.ccfit.android.fitwiki.view.base.IBaseView

interface IViewUserProfileView: IBaseView<IViewUserProfilePresenter> {
    fun showProfile(user: UserInfo, articles: List<Article>)

    fun showErrorScreen()

    fun openArticle(article: Article)

    fun updateMyPermission(myself: UserInfo?)

    fun cannotBan()
}