package ru.nsu.ccfit.android.fitwiki.api.clientmodel.users

import java.net.URL
import java.sql.Timestamp
import java.util.*

class UserData(
        var username: String,
        var registration: Timestamp,
        var photoURL: URL?,
        var role: Role,
        var rating: Int,
        var id: UUID
){
    enum class Role(var id: Int){
        COMMON(0),
        BANNED(1),
        MODERATOR(2),
        ADMIN(3)
    }
}