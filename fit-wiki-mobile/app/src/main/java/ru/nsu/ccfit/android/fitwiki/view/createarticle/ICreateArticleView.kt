package ru.nsu.ccfit.android.fitwiki.view.createarticle

import ru.nsu.ccfit.android.fitwiki.view.base.IBaseView

interface ICreateArticleView: IBaseView<ICreateArticlePresenter> {
    fun setUpSections(sections: List<String>)

    fun dispose()
}