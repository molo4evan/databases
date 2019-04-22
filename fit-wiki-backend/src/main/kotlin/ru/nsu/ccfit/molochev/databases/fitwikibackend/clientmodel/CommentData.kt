package ru.nsu.ccfit.molochev.databases.fitwikibackend.clientmodel

import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Comment
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
    constructor(c: Comment): this(
            c.id,
            c.text,
            c.publication,
            c.article.id,
            c.user.id,
            c.user.username
    )
}