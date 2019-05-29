package ru.nsu.ccfit.android.fitwiki.common.usecases.articles

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.model.Article

class LoadOffered(private val controller: IArticleController): UseCase<LoadOffered.RequestValues, LoadOffered.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        controller.loadOfferedArticles(object : IArticleController.ILoadOfferedArticlesCallback {
            override fun onOfferedArticlesLoaded(articles: List<Article>) {
                val response = ResponseValues(articles)
                useCaseCallback.onSuccess(response)
            }
        })
    }

    class RequestValues: UseCase.RequestValues

    class ResponseValues(val articles: List<Article>): UseCase.ResponseValues
}