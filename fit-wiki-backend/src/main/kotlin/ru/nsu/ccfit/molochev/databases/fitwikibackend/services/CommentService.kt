package ru.nsu.ccfit.molochev.databases.fitwikibackend.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions.NotFoundException
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Comment
import ru.nsu.ccfit.molochev.databases.fitwikibackend.repos.CommentRepository
import java.util.*

@Service
class CommentService {
    @Autowired
    private lateinit var commentRepository: CommentRepository

    @Autowired
    private lateinit var articleService: ArticleService
    @Autowired
    private lateinit var userService: UserService

    fun getCommentByID(id: UUID): Comment{
        val comment = commentRepository.findById(id)
        if (comment is Comment) return comment
        throw NotFoundException(id)
    }

    fun getArticleComments(articleID: UUID): List<Comment> {
        val article = articleService.getArticleByID(articleID)
        return commentRepository.findAllByArticle(article)
    }

    fun addComment(articleID: UUID, userID: UUID, text: String): Comment{
        val article = articleService.getArticleByID(articleID)
        val user = userService.getUserByID(userID)
        val comment = Comment(text, article, user)
        return commentRepository.save(comment)
    }

    fun editComment(id: UUID, text: String): Comment {
        val comment = commentRepository.findById(id)
        if (!comment.isPresent) throw NotFoundException(id)
        comment.get().text = text
        return commentRepository.save(comment.get())
    }

    fun deleteComment(id: UUID){
        commentRepository.deleteById(id)
    }
}