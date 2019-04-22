package ru.nsu.ccfit.molochev.databases.fitwikibackend.repos

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Article
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Comment
import java.util.*

interface CommentRepository: JpaRepository<Comment, UUID> {
    fun findAllByArticle(article: Article): List<Comment>
}