package ru.nsu.ccfit.android.fitwiki.control.backups

import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.model.Backup

interface IBackupController: IController {
    interface ILoadBackupsCallback{
        fun onBackupsLoaded(backups: List<Backup>)
    }

    interface IAddBackupCallback {
        fun onBackupAdded(backup: Backup?)
    }

    interface IRecoverCallback {
        fun onRecovered()
    }

    fun loadBackups(callback: ILoadBackupsCallback)

    fun addBackup(backupName: String, callback: IAddBackupCallback)

    fun recover(backupID: String, callback: IRecoverCallback)
}