package ru.nsu.ccfit.molochev.databases.fitwikibackend.clientmodel.articles

import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Article
import java.sql.Timestamp
import java.util.*

class PublishData(
        var title: String,
        var text: String,
        var summary: String?,
        var sectionId: UUID
) {
    constructor(a: Article): this(
            a.title,
            a.text,
            a.summary,
            a.section.id
    )
}