package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.sections

import ru.nsu.ccfit.android.fitwiki.model.UserInfo
import ru.nsu.ccfit.android.fitwiki.view.base.IBaseView

interface ISectionsView: IBaseView<ISectionsPresenter> {
    fun showSections(sectionNames: List<String>)

    fun openSection(section: String)

    fun setupFabVisibility(user: UserInfo?)

    fun addSection()
}