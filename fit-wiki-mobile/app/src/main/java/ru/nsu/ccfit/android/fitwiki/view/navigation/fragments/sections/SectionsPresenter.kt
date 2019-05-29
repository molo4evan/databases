package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.sections

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.IUseCaseCallback
import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCaseHandler
import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.control.articles.IArticleController
import ru.nsu.ccfit.android.fitwiki.common.usecases.articles.AddSection
import ru.nsu.ccfit.android.fitwiki.common.usecases.articles.LoadSections
import ru.nsu.ccfit.android.fitwiki.common.usecases.users.LoadCurrentUser
import ru.nsu.ccfit.android.fitwiki.view.base.LoadUserPresenter
import java.lang.Exception

class SectionsPresenter(private val view: ISectionsView): ISectionsPresenter, LoadUserPresenter(){
    private lateinit var controllers: Map<IController.ControllerType, IController>

    //region ISectionsPresenter
    override fun setControllers(controllers: Map<IController.ControllerType, IController>) {
        super.setControllers(controllers)
        this.controllers = controllers
    }

    override fun start() {
        super.loadCurrentUserInfo(object : IUseCaseCallback<LoadCurrentUser.ResponseValues>{
            override fun onSuccess(response: LoadCurrentUser.ResponseValues) {
                view.setupFabVisibility(response.user)
            }

            override fun onError() {
                view.setupFabVisibility(null)
            }

        })
        loadSections()
    }

    override fun onSectionSelected(sectionName: String) {
        view.openSection(sectionName)
    }

    override fun onFabPressed() {
        view.addSection()
    }

    override fun addNewSection(section: String) {
        val articleControl = controllers[IController.ControllerType.ARTICLES] as? IArticleController
                ?: throw Exception()    //TODO: stub
        val add = AddSection(articleControl)
        UseCaseHandler.instance.execute(add, AddSection.RequestValues(section),
                object: IUseCaseCallback<AddSection.ResponseValues> {
                    override fun onSuccess(response: AddSection.ResponseValues) {
                        view.showSections(response.sections)
                    }

                    override fun onError() {
                    }
                })
    }
    //endregion

    private fun loadSections(){
        val articleControl = controllers[IController.ControllerType.ARTICLES] as? IArticleController
                ?: throw Exception()    //TODO: stub
        val load = LoadSections(articleControl)
        UseCaseHandler.instance.execute(load, LoadSections.RequestValues(), object: IUseCaseCallback<LoadSections.ResponseValues> {
            override fun onSuccess(response: LoadSections.ResponseValues) {
                view.showSections(response.sections)
            }

            override fun onError() {
            }
        })
    }

}