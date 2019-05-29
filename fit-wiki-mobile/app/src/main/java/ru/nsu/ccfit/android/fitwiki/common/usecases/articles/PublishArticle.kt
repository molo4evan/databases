package ru.nsu.ccfit.android.fitwiki.common.usecases.articles

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.model.Article

class PublishArticle(private val aCtrl: IArticleController): UseCase<PublishArticle.RequestValues, PublishArticle.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        aCtrl.publishOfferedArticle(requestValues.articleID,
                object : IArticleController.IPublishOfferedArticleCallback{
                    override fun onOfferedArticlePublished() {
                        useCaseCallback.onSuccess(ResponseValues())
                    }
                })
    }

    class RequestValues(val articleID: String): UseCase.RequestValues

    class ResponseValues: UseCase.ResponseValues
}