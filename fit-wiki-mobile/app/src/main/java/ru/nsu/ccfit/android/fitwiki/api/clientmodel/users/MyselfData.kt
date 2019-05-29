package ru.nsu.ccfit.android.fitwiki.api.clientmodel.users

import java.net.URL
import java.sql.Timestamp
import java.util.*

class MyselfData(
        var username: String,
        var password: String,
        var registration: Timestamp,
        var photoURL: URL?,
        var role: UserData.Role,
        var rating: Int,
        var id: UUID
){
}