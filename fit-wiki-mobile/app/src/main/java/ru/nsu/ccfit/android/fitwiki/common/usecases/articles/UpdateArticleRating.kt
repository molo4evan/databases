package ru.nsu.ccfit.android.fitwiki.common.usecases.articles

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController

class UpdateArticleRating(private val aCtrl: IArticleController):
        UseCase<UpdateArticleRating.RequestValues, UpdateArticleRating.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        aCtrl.updateArticleRating(requestValues.articleID, requestValues.increase,
                object: IArticleController.IUpdateArticleRatingCallback {
                    override fun onArticleRatingUpdated(newRating: Int) {
                        useCaseCallback.onSuccess(ResponseValues(newRating))
                    }

                })
    }

    class RequestValues(val articleID: String, val increase: Boolean): UseCase.RequestValues

    class ResponseValues(val newRating: Int): UseCase.ResponseValues
}