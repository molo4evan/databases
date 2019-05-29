package ru.nsu.ccfit.android.fitwiki.model

import java.util.*

data class Backup(
        val id: String = UUID.randomUUID().toString(),
        val name: String,
        val date: Date = Date()
)