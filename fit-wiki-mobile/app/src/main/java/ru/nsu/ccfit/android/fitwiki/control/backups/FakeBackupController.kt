package ru.nsu.ccfit.android.fitwiki.control.backups

import ru.nsu.ccfit.android.fitwiki.common.netstub.FakeBackupsRepo
import ru.nsu.ccfit.android.fitwiki.model.Backup

class FakeBackupController: IBackupController {
    override fun loadBackups(callback: IBackupController.ILoadBackupsCallback) {
        callback.onBackupsLoaded(FakeBackupsRepo.backups.values.toList())
    }

    override fun addBackup(backupName: String, callback: IBackupController.IAddBackupCallback) {
        val backup = Backup(name = backupName)
        FakeBackupsRepo.backups[backup.id] = backup
        callback.onBackupAdded(backup)
    }

    override fun recover(backupID: String, callback: IBackupController.IRecoverCallback) {
        callback.onRecovered()
    }
}