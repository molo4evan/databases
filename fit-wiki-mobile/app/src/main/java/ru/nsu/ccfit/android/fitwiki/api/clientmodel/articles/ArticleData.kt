package ru.nsu.ccfit.android.fitwiki.api.clientmodel.articles

import ru.nsu.ccfit.android.fitwiki.model.Article
import java.sql.Timestamp
import java.util.*

class ArticleData(
        var id: UUID,
        var title: String,
        var text: String,
        var summary: String?,
        var creation: Timestamp,
        var published: Boolean,
        var rating: Int,
        var sectionId: UUID,
        var sectionName: String,    //TODO: !!!
        var authorID: UUID?,
        var authorName: String?
) {
    fun toArticle() = Article(
            id.toString(),
            title,
            text,
            authorID.toString(),
            sectionName,
            summary,
            rating,
            creation
    )
}