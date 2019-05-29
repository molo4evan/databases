package ru.nsu.ccfit.android.fitwiki.control.users

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.nsu.ccfit.android.fitwiki.App
import ru.nsu.ccfit.android.fitwiki.api.clientmodel.users.*
import ru.nsu.ccfit.android.fitwiki.model.UserInfo
import java.lang.Exception
import java.net.URL
import java.util.*

class RetroFitUserControler: IUserController {
    override fun loadUsers(callback: IUserController.ILoadUsersCallback) {
        if (App.userToken != null){
            App.api.getUsers(App.userToken!!).enqueue(object : Callback<List<UserData>> {
                override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
                    callback.onUsersLoaded(listOf())
                }

                override fun onResponse(call: Call<List<UserData>>, response: Response<List<UserData>>) {
                    if (response.body() != null){
                        val users = response.body()!!
                        callback.onUsersLoaded(users.map {
                            UserInfo(
                                    it.id.toString(),
                                    it.username,
                                    it.rating,
                                    it.registration,
                                    it.role == UserData.Role.BANNED,
                                    it.role == UserData.Role.MODERATOR,
                                    it.role == UserData.Role.ADMIN,
                                    it.photoURL?.toString()
                            )
                        })
                    } else {
                        callback.onUsersLoaded(listOf())
                    }
                }

            })
        } else {
            callback.onUsersLoaded(listOf())
        }
    }

    override fun loadUserByID(userID: String, callback: IUserController.ILoadUserByIDCallback) {
        App.api.getUser(UUID.fromString(userID)).enqueue(object : Callback<UserData> {
            override fun onFailure(call: Call<UserData>, t: Throwable) {
                callback.onUserLoaded(null)
            }

            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.body() != null){
                    val role = response.body()!!.role
                    val user = UserInfo(
                            response.body()!!.id.toString(),
                            response.body()!!.username,
                            response.body()!!.rating,
                            response.body()!!.registration,
                            role == UserData.Role.BANNED,
                            role == UserData.Role.MODERATOR,
                            role == UserData.Role.ADMIN,
                            response.body()!!.photoURL?.toString()
                    )
                    callback.onUserLoaded(user)
                } else {
                    callback.onUserLoaded(null)
                }
            }
        })
    }

    override fun loadUserByName(username: String, callback: IUserController.ILoadUserByNameCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadCurrentUser(callback: IUserController.ILoadCurrentUserCallback) {
        if (App.userToken != null){
            App.api.getMyself(App.userToken!!).enqueue(object : Callback<MyselfData> {
                override fun onFailure(call: Call<MyselfData>, t: Throwable) {
                    callback.onUserLoaded(null)
                }

                override fun onResponse(call: Call<MyselfData>, response: Response<MyselfData>) {
                    if (response.body() != null){
                        val role = response.body()!!.role
                        val user = UserInfo(
                                response.body()!!.id.toString(),
                                response.body()!!.username,
                                response.body()!!.rating,
                                response.body()!!.registration,
                                role == UserData.Role.BANNED,
                                role == UserData.Role.MODERATOR,
                                role == UserData.Role.ADMIN,
                                response.body()!!.photoURL?.toString()
                        )
                        callback.onUserLoaded(user)
                    } else {
                        callback.onUserLoaded(null)
                    }
                }

            })
        } else {
            callback.onUserLoaded(null)
        }
    }

    override fun loadOrCreateCurrentUser(callback: IUserController.ILoadCurrentUserCallback) {
        throw Exception("deprecated")
    }

    override fun register(username: String, password: String, photoURL: URL?, callback: IUserController.IRegisterCallback) {
        val regData = RegistrationData(username, password, photoURL)
        App.api.register(regData).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback.onRegister(false)
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.body() != null){
                    App.userId = response.body()!!.id
                    App.userToken = response.body()!!.token
                    callback.onRegister(true)
                } else {
                    callback.onRegister(false)
                }
            }

        })
    }

    override fun login(username: String, password: String, callback: IUserController.ILoginCallback) {
        val logData = LoginRequest(username, password)
        App.api.login(logData).enqueue(object : Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback.onLogin(false)
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.body() != null){
                    App.userId = response.body()!!.id
                    App.userToken = response.body()!!.token
                    callback.onLogin(true)
                } else {
                    callback.onLogin(false)
                }
            }

        })
    }

    override fun logout(callback: IUserController.ILogoutCallBack) {
        if (App.userToken != null){
            App.api.logout(App.userToken!!).enqueue(object: Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callback.onLogout()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    App.userToken = null
                    App.userId = null
                    callback.onLogout()
                }

            })
        } else {
            callback.onLogout()
        }
    }

    override fun setBanned(userID: String, ban: Boolean, callback: IUserController.ISetBannedCallback) {
        if (App.userToken != null){
            App.api.changeUserStatus(
                    App.userToken!!,
                    UUID.fromString(userID),
                    if (ban) UserData.Role.BANNED else UserData.Role.COMMON
            ).enqueue(object : Callback<UserData> {
                override fun onFailure(call: Call<UserData>, t: Throwable) {
                }

                override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                    callback.onBannedSet()
                }
            })
        }
    }

    override fun setModerator(userID: String, moder: Boolean, callback: IUserController.ISetModeratorCallback) {
        if (App.userToken != null){
            App.api.changeUserStatus(
                    App.userToken!!,
                    UUID.fromString(userID),
                    if (moder) UserData.Role.MODERATOR else UserData.Role.COMMON
            ).enqueue(object : Callback<UserData> {
                override fun onFailure(call: Call<UserData>, t: Throwable) {
                }

                override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                    callback.onModeratorSet()
                }
            })
        }
    }
}