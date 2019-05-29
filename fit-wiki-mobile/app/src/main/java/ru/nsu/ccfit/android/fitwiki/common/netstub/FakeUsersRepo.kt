package ru.nsu.ccfit.android.fitwiki.common.netstub

import ru.nsu.ccfit.android.fitwiki.model.UserInfo

object FakeUsersRepo {
    val users = mutableMapOf<String, UserInfo>()

    init {
        users["0"] = UserInfo("0", "Vova", 2)
        users["1"] = UserInfo("1", "Some user", photoUrl = "https://www.tsk-energo.ru/wp-content/uploads/2018/08/6ip5X98LT.png")
        users["2"] = UserInfo("2", "Moder", isModerator = true)
        users["3"] = UserInfo("3", "Admin", isAdmin = true)
        users["4"] = UserInfo("4", "Vassermann", 5, isBanned = true)
    }
}