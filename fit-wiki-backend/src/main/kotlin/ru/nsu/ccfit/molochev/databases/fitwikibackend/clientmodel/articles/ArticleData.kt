package ru.nsu.ccfit.molochev.databases.fitwikibackend.clientmodel.articles

import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Article
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
        var sectionName: String,
        var authorID: UUID?,
        var authorName: String?
) {
    constructor(article: Article, authorId: UUID?, sectionId: UUID, sectionName: String): this(
            article.id,
            article.title,
            article.text,
            article.summary,
            article.creation,
            article.published,
            article.rating,
            sectionId,
            sectionName,
            authorId,
            article.authorName
    )

    constructor(article: Article): this(article, article.author?.id, article.section.id, article.section.name)
}