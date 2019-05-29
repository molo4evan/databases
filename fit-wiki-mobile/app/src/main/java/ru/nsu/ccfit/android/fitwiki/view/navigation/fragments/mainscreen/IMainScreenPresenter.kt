package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.mainscreen

import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.view.base.IBasePresenter

interface IMainScreenPresenter: IBasePresenter {
    fun onArticleChosen(article: Article)
}