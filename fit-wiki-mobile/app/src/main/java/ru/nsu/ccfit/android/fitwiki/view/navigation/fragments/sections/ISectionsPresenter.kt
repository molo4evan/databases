package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.sections

import ru.nsu.ccfit.android.fitwiki.view.base.IBasePresenter

interface ISectionsPresenter: IBasePresenter {
    fun onSectionSelected(sectionName: String)

    fun onFabPressed()

    fun addNewSection(section: String)
}