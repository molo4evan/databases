package ru.nsu.ccfit.android.fitwiki.control.backups

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.nsu.ccfit.android.fitwiki.App
import ru.nsu.ccfit.android.fitwiki.api.clientmodel.BackBackup
import ru.nsu.ccfit.android.fitwiki.model.Backup
import java.util.*

class RetroFitBackupController: IBackupController {
    override fun loadBackups(callback: IBackupController.ILoadBackupsCallback) {
        if (App.userToken != null){
            App.api.getBackups(App.userToken!!).enqueue(object : Callback<List<BackBackup>> {
                override fun onFailure(call: Call<List<BackBackup>>, t: Throwable) {
                    callback.onBackupsLoaded(listOf())
                }

                override fun onResponse(call: Call<List<BackBackup>>, response: Response<List<BackBackup>>) {
                    if (response.body() != null){
                        callback.onBackupsLoaded(response.body()!!.map { Backup(it.id.toString(), it.name, it.creation) })
                    } else {
                        callback.onBackupsLoaded(listOf())
                    }
                }

            })
        } else {
            callback.onBackupsLoaded(listOf())
        }
    }

    override fun addBackup(backupName: String, callback: IBackupController.IAddBackupCallback) {
        if (App.userToken != null){
            App.api.backup(App.userToken!!, backupName).enqueue(object : Callback<BackBackup> {
                override fun onFailure(call: Call<BackBackup>, t: Throwable) {
                    callback.onBackupAdded(null)
                }

                override fun onResponse(call: Call<BackBackup>, response: Response<BackBackup>) {
                    if (response.body() != null){
                        callback.onBackupAdded(Backup(
                                response.body()!!.id.toString(),
                                response.body()!!.name,
                                response.body()!!.creation
                        ))
                    } else {
                        callback.onBackupAdded(null)
                    }
                }

            })
        } else {
            callback.onBackupAdded(null)
        }
    }

    override fun recover(backupID: String, callback: IBackupController.IRecoverCallback) {
        if (App.userToken != null){
            App.api.restore(App.userToken!!, UUID.fromString(backupID)).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) callback.onRecovered()
                }

            })
        } else {
        }
    }
}