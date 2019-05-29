package ru.nsu.ccfit.android.fitwiki.view.viewmyprofile

import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.view.base.IBasePresenter

interface IViewMyProfilePresenter: IBasePresenter {
    fun onArticleSelected(article: Article)

    fun loadUser()
}