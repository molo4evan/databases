package ru.nsu.ccfit.android.fitwiki.api.clientmodel.articles

import java.sql.Timestamp
import java.util.*

class PublishData(
        var title: String,
        var text: String,
        var summary: String?,
        var sectionId: UUID
) {
}