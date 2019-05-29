package ru.nsu.ccfit.molochev.databases.fitwikibackend.model

import javax.persistence.*

@Entity
@Table(name = "rating_history")
class Rating (
        @EmbeddedId
        var id: PostingID,

        val increment: Boolean,

        @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "article_id", insertable = false, updatable = false)
        val article: Article,

        @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "user_id", insertable = false, updatable = false)
        val user: User
){
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Rating

                if (id != other.id) return false

                return true
        }

        override fun hashCode(): Int {
                return id.hashCode()
        }
}