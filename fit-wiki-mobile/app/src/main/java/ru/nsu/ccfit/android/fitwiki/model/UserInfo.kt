package ru.nsu.ccfit.android.fitwiki.model

import java.util.*

data class UserInfo(
        val id: String = UUID.randomUUID().toString(),
        val username: String,
        val rating: Int = 0,
        val registrationDate: Date = Date(),
        val isBanned: Boolean = false,
        val isModerator: Boolean = false,
        val isAdmin: Boolean = false,
        val photoUrl: String? = null
)