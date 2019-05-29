package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.backups

import ru.nsu.ccfit.android.fitwiki.common.usecasebase.IUseCaseCallback
import ru.nsu.ccfit.android.fitwiki.common.usecasebase.UseCaseHandler
import ru.nsu.ccfit.android.fitwiki.common.usecases.backups.AddBackup
import ru.nsu.ccfit.android.fitwiki.common.usecases.backups.LoadBackups
import ru.nsu.ccfit.android.fitwiki.common.usecases.backups.Recover
import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.control.backups.IBackupController
import ru.nsu.ccfit.android.fitwiki.model.Backup

class BackupsPresenter(private val view: IBackupsView):IBackupsPresenter {
    private lateinit var bCtrl: IBackupController

    override fun onFabPressed() {
        view.addBackup()
    }

    override fun addNewBackup(backupName: String) {
        val add = AddBackup(bCtrl)
        UseCaseHandler.instance.execute(
                add,
                AddBackup.RequestValues(backupName),
                object : IUseCaseCallback<AddBackup.ResponseValues>{
                    override fun onSuccess(response: AddBackup.ResponseValues) {
                        loadBackups()
                    }

                    override fun onError() {
                    }
                }
        )
    }

    override fun setControllers(controllers: Map<IController.ControllerType, IController>) {
        bCtrl = controllers[IController.ControllerType.BACKUPS] as? IBackupController
                ?: throw Exception()
    }

    override fun start() {
        loadBackups()
    }

    override fun loadBackups() {
        val load = LoadBackups(bCtrl)
        UseCaseHandler.instance.execute(
                load,
                LoadBackups.RequestValues(),
                object : IUseCaseCallback<LoadBackups.ResponseValues>{
                    override fun onSuccess(response: LoadBackups.ResponseValues) {
                        view.showBackups(response.backups)
                    }

                    override fun onError() {
                        //view.showError()
                    }
                }
        )
    }

    override fun onBackupSelected(backup: Backup) {
        recoverBackup(backup)
    }

    private fun recoverBackup(backup: Backup) {
        val recover = Recover(bCtrl)
        UseCaseHandler.instance.execute(
                recover,
                Recover.RequestValues(backup),
                object : IUseCaseCallback<Recover.ResponseValues>{
                    override fun onSuccess(response: Recover.ResponseValues) {
                    }

                    override fun onError() {
                    }
                }
        )
    }
}