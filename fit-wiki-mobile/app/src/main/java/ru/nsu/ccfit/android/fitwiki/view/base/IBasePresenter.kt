package ru.nsu.ccfit.android.fitwiki.view.base

import ru.nsu.ccfit.android.fitwiki.control.IController

interface IBasePresenter {
    fun setControllers(controllers: Map<IController.ControllerType, IController>)

    fun start()
}