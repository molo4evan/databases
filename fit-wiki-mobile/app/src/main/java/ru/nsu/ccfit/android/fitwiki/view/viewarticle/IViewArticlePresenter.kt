package ru.nsu.ccfit.android.fitwiki.view.viewarticle

import ru.nsu.ccfit.android.fitwiki.view.base.IBasePresenter

interface IViewArticlePresenter: IBasePresenter {
    fun onUserProfileSelected()

    fun onIncreaseArticleRating()

    fun onDecreaseArticleRating()
}