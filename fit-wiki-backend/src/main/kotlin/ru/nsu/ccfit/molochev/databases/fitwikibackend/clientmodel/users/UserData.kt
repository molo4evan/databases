package ru.nsu.ccfit.molochev.databases.fitwikibackend.clientmodel.users

import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.User
import java.net.URL
import java.sql.Timestamp
import java.util.*

class UserData(
        var username: String,
        var registration: Timestamp,
        var photoURL: URL?,
        var role: User.Role,
        var rating: Int,
        var id: UUID
){
    constructor(user: User): this(
            user.username,
            user.registration,
            user.photoURL,
            user.role,
            user.rating,
            user.id
    )
}