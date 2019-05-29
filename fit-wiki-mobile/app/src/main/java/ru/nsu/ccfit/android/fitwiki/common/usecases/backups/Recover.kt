package ru.nsu.ccfit.android.fitwiki.common.usecases.backups

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.backups.IBackupController
import ru.nsu.ccfit.android.fitwiki.model.Backup

class Recover(private val bCtrl: IBackupController): UseCase<Recover.RequestValues, Recover.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        bCtrl.recover(requestValues.backup.id, object : IBackupController.IRecoverCallback{
            override fun onRecovered() {
                useCaseCallback.onSuccess(ResponseValues())
            }
        })
    }

    class RequestValues(val backup: Backup): UseCase.RequestValues

    class ResponseValues: UseCase.ResponseValues
}