package ru.nsu.ccfit.android.fitwiki.api.clientmodel

import java.sql.Timestamp
import java.util.*

class BackBackup(
        var name: String,
        var creation: Timestamp,
        var id: UUID = UUID.randomUUID()
)