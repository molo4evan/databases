package ru.nsu.ccfit.android.fitwiki.common.usecases.backups

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.backups.IBackupController
import ru.nsu.ccfit.android.fitwiki.model.Backup

class AddBackup(private val bCtrl: IBackupController): UseCase<AddBackup.RequestValues, AddBackup.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        bCtrl.addBackup(requestValues.backupName, object : IBackupController.IAddBackupCallback{
            override fun onBackupAdded(backup: Backup?) {
                useCaseCallback.onSuccess(ResponseValues())
            }
        })
    }

    class RequestValues(val backupName: String): UseCase.RequestValues

    class ResponseValues: UseCase.ResponseValues
}