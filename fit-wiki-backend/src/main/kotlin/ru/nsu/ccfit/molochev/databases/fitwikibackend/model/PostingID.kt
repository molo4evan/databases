package ru.nsu.ccfit.molochev.databases.fitwikibackend.model

import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class PostingID(
        @Column(name = "article_id")
        var articleID: UUID,
        @Column(name = "user_id")
        var userID: UUID
): Serializable {
        override fun equals(other: Any?) =
                other is PostingID && articleID == other.articleID && userID == other.userID

        override fun hashCode() = 31 * articleID.hashCode() + userID.hashCode()
}