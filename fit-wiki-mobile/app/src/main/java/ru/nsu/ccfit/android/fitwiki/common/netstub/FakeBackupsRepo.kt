package ru.nsu.ccfit.android.fitwiki.common.netstub

import ru.nsu.ccfit.android.fitwiki.model.Backup

object FakeBackupsRepo {
    val backups = mutableMapOf<String, Backup>()

    init {
        backups["0"] = Backup(name = "First")
        backups["1"] = Backup(name = "Second")
        backups["2"] = Backup(name = "Third")
        backups["3"] = Backup(name = "Best f*ckn backup in the entire world!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        backups["4"] = Backup(name = "Last")
    }
}