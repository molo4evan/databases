package ru.nsu.ccfit.android.fitwiki.common.usecases.articles

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController

class LoadSections(private val controller: IArticleController): UseCase<LoadSections.RequestValues, LoadSections.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        controller.loadSectionNames(object: IArticleController.ILoadSectionNamesCallback{
            override fun onSectionNamesLoaded(sectionNames: List<String>) {
                val response = ResponseValues(sectionNames)
                useCaseCallback.onSuccess(response)
            }
        })
    }

    class RequestValues: UseCase.RequestValues

    class ResponseValues(val sections: List<String>): UseCase.ResponseValues
}