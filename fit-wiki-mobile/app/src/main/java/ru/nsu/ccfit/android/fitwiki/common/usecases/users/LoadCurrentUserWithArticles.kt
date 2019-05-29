package ru.nsu.ccfit.android.fitwiki.common.usecases.users

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.control.users.IUserController
import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.model.UserInfo

class LoadCurrentUserWithArticles(private val uCtrl: IUserController, private val aCtrl: IArticleController):
        UseCase<LoadCurrentUserWithArticles.RequestValues, LoadCurrentUserWithArticles.ResponseValues>(){
    override fun executeUseCase(requestValues: RequestValues) {
        uCtrl.loadCurrentUser(object : IUserController.ILoadCurrentUserCallback {
            override fun onUserLoaded(user: UserInfo?) {
                if (user == null){
                    useCaseCallback.onError()
                } else {
                    aCtrl.loadUserArticles(user.id, object : IArticleController.ILoadUserArticlesCallback {
                        override fun onArticlesLoaded(articles: List<Article>) {
                            useCaseCallback.onSuccess(ResponseValues(user, articles))
                        }
                    })
                }
            }
        })
    }

    class RequestValues: UseCase.RequestValues

    class ResponseValues(val user: UserInfo, val articles: List<Article>): UseCase.ResponseValues
}