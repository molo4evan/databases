package ru.nsu.ccfit.android.fitwiki.api.clientmodel.articles

import java.sql.Timestamp
import java.util.*

class CommentData (
        var id: UUID,
        var text: String,
        var publication: Timestamp,
        var articleId: UUID,
        var userId: UUID,
        var userName: String
) {
}