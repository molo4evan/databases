package ru.nsu.ccfit.android.fitwiki.view.viewuserprofile

import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.view.base.IBasePresenter

interface IViewUserProfilePresenter: IBasePresenter {
    fun onArticleSelected(article: Article)

    fun onSetBanned(state: Boolean)

    fun onSetModerator(state: Boolean)

    fun loadUser()

    fun loadMyself()
}