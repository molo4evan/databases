package ru.nsu.ccfit.android.fitwiki.common.usecases.users

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController
import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.model.UserInfo

class LoadUserByID(
        private val aCtrl: IArticleController,
        private val uCtrl: IUserController
): UseCase<LoadUserByID.RequestValues, LoadUserByID.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        uCtrl.loadUserByID(requestValues.id, object : IUserController.ILoadUserByIDCallback{
            override fun onUserLoaded(user: UserInfo?) {
                if (user == null) {
                    useCaseCallback.onError()
                    return
                }
                aCtrl.loadUserArticles(user.id, object : IArticleController.ILoadUserArticlesCallback {
                    override fun onArticlesLoaded(articles: List<Article>) {
                        useCaseCallback.onSuccess(ResponseValues(user, articles))
                    }
                })
            }

        })
    }

    class RequestValues(val id: String): UseCase.RequestValues

    class ResponseValues(val user: UserInfo, val articles: List<Article>): UseCase.ResponseValues
}