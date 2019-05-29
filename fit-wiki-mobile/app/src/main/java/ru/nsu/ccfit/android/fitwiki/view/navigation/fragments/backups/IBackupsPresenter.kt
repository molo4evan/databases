package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.backups

import ru.nsu.ccfit.android.fitwiki.model.Backup
import ru.nsu.ccfit.android.fitwiki.view.base.IBasePresenter

interface IBackupsPresenter: IBasePresenter {
    fun onBackupSelected(backup: Backup)

    fun onFabPressed()

    fun addNewBackup(backupName: String)

    fun loadBackups()
}