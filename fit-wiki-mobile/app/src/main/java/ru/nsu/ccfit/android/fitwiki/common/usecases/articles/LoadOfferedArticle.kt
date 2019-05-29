package ru.nsu.ccfit.android.fitwiki.common.usecases.articles

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController
import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.model.UserInfo

class LoadOfferedArticle(
        private val aCtrl: IArticleController,
        private val uCtrl: IUserController
): UseCase<LoadOfferedArticle.RequestValues, LoadOfferedArticle.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        aCtrl.loadOfferedArticle(requestValues.articleID, object : IArticleController.ILoadOfferedArticleCallback{
            override fun onOfferedArticleLoaded(article: Article?) {
                if (article == null) {
                    useCaseCallback.onError()
                } else {
                    uCtrl.loadUserByID(article.authorId, object : IUserController.ILoadUserByIDCallback{
                        override fun onUserLoaded(user: UserInfo?) {
                            if (user == null) {
                                useCaseCallback.onError()
                            } else {
                                useCaseCallback.onSuccess(ResponseValues(article, user))
                            }
                        }
                    })
                }
            }
        })
    }

    class RequestValues(val articleID: String): UseCase.RequestValues

    class ResponseValues(val article: Article, val user: UserInfo): UseCase.ResponseValues
}