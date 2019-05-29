package ru.nsu.ccfit.android.fitwiki.common.usecases.articles

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.model.Article

class EditArticle(private val ctrl: IArticleController): UseCase<EditArticle.RequestValues, EditArticle.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        ctrl.editArticle(
                requestValues.newState,
                object : IArticleController.IEditArticleCallback{
                    override fun onArticleEdited(success: Boolean) {
                        if (success){
                            useCaseCallback.onSuccess(ResponseValues())
                        } else {
                            useCaseCallback.onError()
                        }
                    }
                }
        )
    }

    class RequestValues(val newState: Article): UseCase.RequestValues

    class ResponseValues: UseCase.ResponseValues
}