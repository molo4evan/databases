package ru.nsu.ccfit.android.fitwiki.control

interface IController {
    enum class ControllerType {
        ARTICLES,
        USERS,
        BACKUPS
    }
}