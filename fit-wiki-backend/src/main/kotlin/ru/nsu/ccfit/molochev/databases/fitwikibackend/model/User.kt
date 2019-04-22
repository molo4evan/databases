package ru.nsu.ccfit.molochev.databases.fitwikibackend.model

import java.net.URL
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
        var username: String,
        var password: String,
        var registration: Timestamp = Timestamp.from(Date().toInstant()),
        @Column(name = "photo_url")
        var photoURL: URL? = null,
        @Enumerated var role: Role = Role.COMMON,
        var rating: Int = 0,

        @Id @GeneratedValue var id: UUID = UUID.randomUUID(),

        @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var articles: MutableSet<Article>? = null,

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var comments: MutableSet<Comment>? = null,

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var ratings: MutableSet<Rating>? = null
){
    enum class Role(var id: Int){
        COMMON(0),
        BANNED(1),
        MODERATOR(2),
        ADMIN(3)
    }

    fun isAdmin() = role == Role.ADMIN

    fun isPrivileged() = role == Role.ADMIN || role == Role.MODERATOR

    fun isBanned() = role == Role.BANNED

    fun roleCanBeChanged(other: Role) = role != Role.ADMIN
            && !((role == Role.MODERATOR && other == Role.BANNED)
            || (role == Role.BANNED && other == Role.MODERATOR))
}