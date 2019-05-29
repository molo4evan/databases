package ru.nsu.ccfit.android.fitwiki.common.usecases.articles

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.model.Article

class LoadSection(
        private val controller: IArticleController
): UseCase<LoadSection.RequestValues, LoadSection.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        controller.loadSection(requestValues.sectionName, object: IArticleController.ILoadSectionCallback {
            override fun onSectionLoaded(section: List<Article>?) {
                if (section != null) {
                    val response = ResponseValues(section)
                    useCaseCallback.onSuccess(response)
                } else {
                    useCaseCallback.onError()
                }
            }
        })
    }

    class RequestValues(val sectionName: String): UseCase.RequestValues

    class ResponseValues(val articles: List<Article>): UseCase.ResponseValues
}