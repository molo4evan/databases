package ru.nsu.ccfit.molochev.databases.fitwikibackend.clientmodel.users

import java.net.URL

class RegistrationData(
        var username: String,
        var password: String,
        var photoURL: URL?
)