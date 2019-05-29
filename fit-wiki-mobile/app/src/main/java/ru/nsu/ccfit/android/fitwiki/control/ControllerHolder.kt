package ru.nsu.ccfit.android.fitwiki.control

import ru.nsu.ccfit.android.fitwiki.control.articles.*
import ru.nsu.ccfit.android.fitwiki.control.backups.*
import ru.nsu.ccfit.android.fitwiki.control.users.*

object ControllerHolder {
    val controllers: Map<IController.ControllerType, IController> = mapOf(
            Pair(IController.ControllerType.ARTICLES, RetroFitArticleController()),
            Pair(IController.ControllerType.USERS, RetroFitUserControler()),
            Pair(IController.ControllerType.BACKUPS, RetroFitBackupController())
    )
}