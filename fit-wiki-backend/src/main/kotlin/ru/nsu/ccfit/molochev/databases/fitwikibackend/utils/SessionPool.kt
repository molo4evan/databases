package ru.nsu.ccfit.molochev.databases.fitwikibackend.utils

import java.util.*

object SessionPool {
    val sessions = mutableMapOf<String, UUID>()
}