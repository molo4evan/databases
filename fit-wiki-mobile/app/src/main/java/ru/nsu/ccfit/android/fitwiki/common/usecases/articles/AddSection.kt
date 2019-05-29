package ru.nsu.ccfit.android.fitwiki.common.usecases.articles

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController

class AddSection(private val aCtrl: IArticleController):
        UseCase<AddSection.RequestValues, AddSection.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        aCtrl.addSection(requestValues.sectionName, object: IArticleController.IAddSectionCallback {
            override fun onSectionAdded(updatedSectionList: List<String>) {
                useCaseCallback.onSuccess(ResponseValues(updatedSectionList))
            }
        })
    }

    class RequestValues(val sectionName: String): UseCase.RequestValues

    class ResponseValues(val sections: List<String>): UseCase.ResponseValues
}