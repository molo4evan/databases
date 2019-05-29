package ru.nsu.ccfit.android.fitwiki.common.usecases.articles

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.model.Article

class CancelArticle(private val aCtrl: IArticleController): UseCase<CancelArticle.RequestValues, CancelArticle.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        aCtrl.cancelOfferedArticle(requestValues.articleID,
                object : IArticleController.ICancelOfferedArticleCallback{
                    override fun onOfferedArticleCancelled() {
                        useCaseCallback.onSuccess(ResponseValues())
                    }
                })
    }

    class RequestValues(val articleID: String): UseCase.RequestValues

    class ResponseValues: UseCase.ResponseValues
}