package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.backups

import ru.nsu.ccfit.android.fitwiki.model.Backup
import ru.nsu.ccfit.android.fitwiki.view.base.IBaseView

interface IBackupsView: IBaseView<IBackupsPresenter> {
    fun showBackups(backups: List<Backup>)

    fun addBackup()

    //TODO: mb add method on recovery success?
}