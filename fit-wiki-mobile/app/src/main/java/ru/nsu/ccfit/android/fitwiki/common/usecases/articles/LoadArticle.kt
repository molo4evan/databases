package ru.nsu.ccfit.android.fitwiki.common.usecases.articles

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController
import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.model.UserInfo

class LoadArticle(private val aCtrl: IArticleController, private val uCtrl: IUserController):
        UseCase<LoadArticle.RequestValues, LoadArticle.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        aCtrl.loadArticle(requestValues.sectionName, requestValues.articleID,
                object : IArticleController.ILoadArticleCallback {
                    override fun onArticleLoaded(article: Article?) {
                        if (article != null) {
                            uCtrl.loadUserByID(article.authorId, object : IUserController.ILoadUserByIDCallback {
                                override fun onUserLoaded(user: UserInfo?) {
                                    if (user != null) {
                                        val response = ResponseValues(article, user)
                                        useCaseCallback.onSuccess(response)
                                    } else {
                                        useCaseCallback.onError()
                                    }
                                }
                            })
                        } else {
                            useCaseCallback.onError()
                        }
                    }
                })
    }

    class RequestValues(val articleID: String, val sectionName: String): UseCase.RequestValues

    class ResponseValues(val article: Article, val user: UserInfo): UseCase.ResponseValues
}