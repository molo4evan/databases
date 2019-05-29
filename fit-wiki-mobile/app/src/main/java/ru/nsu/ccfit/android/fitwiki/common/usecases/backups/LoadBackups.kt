package ru.nsu.ccfit.android.fitwiki.common.usecases.backups

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCase
import ru.nsu.ccfit.android.fitwiki.control.backups.IBackupController
import ru.nsu.ccfit.android.fitwiki.model.Backup

class LoadBackups(private val bCtrl: IBackupController): UseCase<LoadBackups.RequestValues, LoadBackups.ResponseValues>() {
    override fun executeUseCase(requestValues: RequestValues) {
        bCtrl.loadBackups(object : IBackupController.ILoadBackupsCallback{
            override fun onBackupsLoaded(backups: List<Backup>) {
                useCaseCallback.onSuccess(ResponseValues(backups))
            }
        })
    }

    class RequestValues: UseCase.RequestValues

    class ResponseValues(val backups: List<Backup>): UseCase.ResponseValues
}