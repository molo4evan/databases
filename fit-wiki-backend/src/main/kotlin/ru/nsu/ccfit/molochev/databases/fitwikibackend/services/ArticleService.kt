package ru.nsu.ccfit.molochev.databases.fitwikibackend.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions.ForbiddenException
import ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions.NotAcceptableException
import ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions.NotFoundException
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Article
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.PostingID
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Rating
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.User
import ru.nsu.ccfit.molochev.databases.fitwikibackend.repos.ArticleRepository
import ru.nsu.ccfit.molochev.databases.fitwikibackend.repos.RatingRepository
import java.lang.Exception
import java.sql.Timestamp
import java.util.*
import javax.transaction.Transactional

@Service
class ArticleService {
    @Autowired
    private lateinit var articleRepository: ArticleRepository
    @Autowired
    private lateinit var ratingRepository: RatingRepository

    @Autowired
    private lateinit var sectionService: SectionService
    @Autowired
    private lateinit var userService: UserService

    fun getArticleByID(id: UUID): Article {
        val article = articleRepository.findById(id)
        if (article.isPresent) return article.get()
        throw NotFoundException(id)
    }

    fun getArticlesInSection(sectionId: UUID): List<Article> {
        val section = sectionService.getSectionById(sectionId)
        return articleRepository.findAllBySectionAndPublishedIsTrue(section)
    }

    fun getArticlesOfUser(userId: UUID): List<Article> {
        val user = userService.getUserByID(userId)
        return articleRepository.findAllByAuthorAndPublishedIsTrue(user)
    }

    fun getBest30Articles() = articleRepository.findTop30ByPublishedIsTrueOrderByRatingDesc()

    fun getBestAticles(first: Int, size: Int): Slice<Article>{
        val page = PageRequest.of(first, size)
        return articleRepository.findTopByPublishedIsTrueOrderByRatingDesc(page)
    }

    fun getOfferedArticles() = articleRepository.findAllByPublishedIsFalse()

    @Transactional
    fun offerArticle(article: Article): Article {
        if (articleRepository.existsById(article.id)) throw NotAcceptableException()
        article.published = false
        article.creation = Timestamp.from(Date().toInstant())
        article.rating = 0
        article.previousVersion = null
        return articleRepository.save(article)
    }

    fun offerArticleChanges(article: Article): Article {
        val last = articleRepository.findById(article.id)
        if (last.isPresent){
            article.previousVersion = last.get().id
            article.id = UUID.randomUUID()
            article.published = false
            return articleRepository.save(article)
        } else {
            throw NotFoundException(article.id)
        }
    }

    @Transactional
    fun publishOfferedArticle(id: UUID): Article{
        val optional = articleRepository.findById(id)
        if (optional.isPresent){
            val article = optional.get()
            if (article.published) throw NotAcceptableException()
            article.published = true
            if (article.previousVersion != null){
                val verID: UUID = article.previousVersion!!
                val previous = articleRepository.findById(verID)
                return if (previous.isPresent) {
                    articleRepository.delete(previous.get())
                    article.previousVersion = null
                    val out = articleRepository.save(article)
                    articleRepository.updatePreviousVersion(verID, article.id)
                    out
                } else {
                    article.previousVersion = null
                    articleRepository.save(article)
                }
            } else {
                return articleRepository.save(article)
            }
        } else throw NotFoundException(id)
    }

    fun declineOfferedArticle(id: UUID){
        val article = articleRepository.findById(id)
        if (!article.isPresent) throw NotFoundException(id)
        if (article.get().published) throw NotAcceptableException()
        articleRepository.delete(article.get())
    }

    @Transactional
    fun deleteArticle(id: UUID, user: User){
        val optional = articleRepository.findById(id)
        if (!optional.isPresent) throw NotFoundException(id)
        val article = optional.get()
        if (!article.published) throw NotAcceptableException()
        if (article.author == null && !user.isPrivileged() || article.author != user){
            throw ForbiddenException()
        }
        articleRepository.deleteAllByPreviousVersion(article.id)
        articleRepository.delete(article)
    }

    fun searchArticles(query: String): List<Article>{
        return articleRepository.search_articles(query)
    }

    @Transactional
    fun rateArticle(articleID: UUID, userID: UUID, increment: Boolean){
        val id = PostingID(articleID, userID)
        val rating = ratingRepository.findById(id)
        if (rating.isPresent){
            if (rating.get().increment == increment) throw ForbiddenException()
            val article = rating.get().article
            article.rating = article.rating + (if (increment) 1 else -1)
            articleRepository.save(article)
            ratingRepository.delete(rating.get())
        } else {
            val optional = articleRepository.findById(articleID)
            if (!optional.isPresent) throw NotFoundException(articleID)
            val article = optional.get()
            val user = userService.getUserByID(userID)
            val newRating = Rating(id, increment, article, user)
            article.rating = article.rating + (if (increment) 1 else -1)
            articleRepository.save(article)
            ratingRepository.save(newRating)
        }
    }
}