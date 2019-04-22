package ru.nsu.ccfit.molochev.databases.fitwikibackend.model

import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity(name = "article")
@Table(name = "articles")
@NamedStoredProcedureQuery(
        name = "search_articles",
        procedureName = "search_articles",
        parameters = [
                StoredProcedureParameter(
                        name = "question",
                        type = String::class,
                        mode = ParameterMode.IN
                )
        ]
)
class Article(
        var title: String,
        var text: String,
        var summary: String? = null,
        var creation: Timestamp = Timestamp.from(Date().toInstant()),
        var published: Boolean = false,
        var rating: Int = 0,

        @Column(name = "author_name")
        var authorName: String? = null,

        @Column(name = "prev_version_id")
        var previousVersion: UUID? = null,

        @Id @GeneratedValue
        var id: UUID = UUID.randomUUID(),

        @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "section_id")
        var section: Section,

        @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "author_id")
        var author: User? = null,

        @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var comments: MutableSet<Comment>? = null,

        @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var ratings: MutableSet<Rating>? = null
)