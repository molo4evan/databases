package ru.nsu.ccfit.android.fitwiki.common.usecases.articles

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.model.Article

class CreateArticle(private val aCtrl: IArticleController): UseCase<CreateArticle.RequestValues, CreateArticle.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        aCtrl.createOfferedArticle(
                requestValues.articleTitle,
                requestValues.articleText,
                requestValues.authorID,
                requestValues.sectionName,
                requestValues.articleSummary,
                object : IArticleController.ICreateArticleCallback {
                    override fun onArticleCreated(article: Article?) {
                        if (article == null){
                            useCaseCallback.onError()
                        } else {
                            useCaseCallback.onSuccess(ResponseValues(article))
                        }
                    }
                }
        )
    }

    class RequestValues(
            val articleTitle: String,
            val articleText: String,
            val authorID: String?,
            val sectionName: String,
            val articleSummary: String?
    ): UseCase.RequestValues

    class ResponseValues(val article: Article): UseCase.ResponseValues
}