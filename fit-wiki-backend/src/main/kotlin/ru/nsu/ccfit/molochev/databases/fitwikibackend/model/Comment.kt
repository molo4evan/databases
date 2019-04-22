package ru.nsu.ccfit.molochev.databases.fitwikibackend.model

import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "commentaries")
class Comment (
        var text: String,

        @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "article_id", insertable = false, updatable = false)
        val article: Article,

        @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "user_id", insertable = false, updatable = false)
        val user: User,

        var publication: Timestamp = Timestamp.from(Date().toInstant()),

        @Id @GeneratedValue
        var id: UUID = UUID.randomUUID()


)

