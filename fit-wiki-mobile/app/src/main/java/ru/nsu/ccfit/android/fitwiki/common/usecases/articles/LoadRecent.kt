package ru.nsu.ccfit.android.fitwiki.common.usecases.articles

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.model.Article

class LoadRecent(private val controller: IArticleController): UseCase<LoadRecent.RequestValues, LoadRecent.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        controller.loadRecent(object : IArticleController.ILoadRecentCallback {
            override fun onRecentLoaded(articles: List<Article>) {
                val response = ResponseValues(articles)
                useCaseCallback.onSuccess(response)
            }
        })
    }

    class RequestValues: UseCase.RequestValues

    class ResponseValues(val articles: List<Article>): UseCase.ResponseValues
}