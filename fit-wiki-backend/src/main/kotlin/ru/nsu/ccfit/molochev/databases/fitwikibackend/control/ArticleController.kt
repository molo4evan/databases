package ru.nsu.ccfit.molochev.databases.fitwikibackend.control

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.nsu.ccfit.molochev.databases.fitwikibackend.clientmodel.articles.*
import ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions.ForbiddenException
import ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions.NotFoundException
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Article
import ru.nsu.ccfit.molochev.databases.fitwikibackend.services.ArticleService
import ru.nsu.ccfit.molochev.databases.fitwikibackend.services.CommentService
import ru.nsu.ccfit.molochev.databases.fitwikibackend.services.SectionService
import java.util.*

@RestController
@RequestMapping("articles")
class ArticleController: CheckingController() {
    @Autowired
    private lateinit var articleService: ArticleService
    @Autowired
    private lateinit var commentService: CommentService
    @Autowired
    private lateinit var sectionService: SectionService

    @GetMapping("/best30")
    fun getBest30Articles(): List<ArticleData>{
        return articleService.getBest30Articles().map(::ArticleData)
    }

    @GetMapping("/best")
    fun getBestArticles(
            @RequestParam first: Int,
            @RequestParam size: Int
    ): List<ArticleData> {
        return articleService.getBestAticles(first, size).map(::ArticleData).toList()
    }

    @GetMapping("offered")
    fun getOfferedArticles(@RequestHeader(value = "WWW-Authenticate") token: String): List<ArticleData> {
        checkUserIsPrivileged(token)
        return articleService.getOfferedArticles().map(::ArticleData)
    }

    @GetMapping("offered/{id}")
    fun getOfferedArticle(
            @RequestHeader(value = "WWW-Authenticate") token: String,
            @PathVariable id: UUID
    ): ArticleData {
        checkUserIsPrivileged(token)
        val offered = articleService.getOfferedArticles()
        for (article in offered) {
            if (article.id == id) return ArticleData(article)
        }
        throw NotFoundException(id)
    }

    @GetMapping("/section/{id}")
    fun getArticlesFromSection(@PathVariable id: UUID): List<ArticleData>{
        return articleService.getArticlesInSection(id).map(::ArticleData)
    }

    @GetMapping("/user/{id}")
    fun getArticlesOfUser(@PathVariable id: UUID): List<ArticleData> {
        return articleService.getArticlesOfUser(id).map(::ArticleData)
    }

    @GetMapping("{id}")
    fun getArticleByID(@PathVariable id: UUID): ArticleData {
        return ArticleData(articleService.getArticleByID(id))
    }

    @GetMapping("/find")
    fun findArticles(@RequestParam text: String): List<ArticleData> {
        return articleService.searchArticles(text).map(::ArticleData)
    }

    @GetMapping("/{id}/comments")
    fun getArticleComments(@PathVariable id: UUID): List<CommentData> {
        return commentService.getArticleComments(id).map(::CommentData)
    }

    @PostMapping("/offer")
    fun offerNewArticle(
            @RequestBody data: PublishData,
            @RequestHeader(value = "WWW-Authenticate") token: String
    ){
        checkUserIsBanned(token)
        val user = getUser(token)
        val section = sectionService.getSectionById(data.sectionId)
        val article = Article(
                data.title,
                data.text,
                data.summary,
                author = user,
                section = section
        )
        articleService.offerArticle(article)
    }

    @PostMapping("/{id}/offer")
    fun offerArticleChanges(
            @PathVariable id: UUID,
            @RequestBody data: PublishData,
            @RequestHeader(value = "WWW-Authenticate") token: String
    ){
        checkUserIsBanned(token)
        val article = articleService.getArticleByID(id)
        article.title = data.title
        article.summary = data.summary
        article.text = data.text
        articleService.offerArticleChanges(article)
    }

    @PostMapping("/{id}/publish")
    fun publishOfferedArticle(
            @PathVariable id: UUID,
            @RequestHeader(value = "WWW-Authenticate") token: String
    ){
        checkUserIsPrivileged(token)
        articleService.publishOfferedArticle(id)
    }

    @PostMapping("/{id}/decline")
    fun declineOfferedArticle(
            @PathVariable id: UUID,
            @RequestHeader(value = "WWW-Authenticate") token: String
    ){
        checkUserIsPrivileged(token)
        articleService.declineOfferedArticle(id)
    }

    @PostMapping("/{id}/delete")
    fun deleteArticle(
            @PathVariable id: UUID,
            @RequestHeader(value = "WWW-Authenticate") token: String
    ){
        val user = getUser(token)
        articleService.deleteArticle(id, user)
    }

    @PostMapping("/{id}/rate")
    fun rateArticle(
            @PathVariable id: UUID,
            @RequestBody data: RateData,
            @RequestHeader(value = "WWW-Authenticate") token: String
    ){
        checkUserIsBanned(token)
        val user = getUser(token)
        articleService.rateArticle(id, user.id, data.up)
    }

    @PostMapping("/{id}/comment")
    fun commentArticle(
            @PathVariable id: UUID,
            @RequestBody data: PubComData,
            @RequestHeader(value = "WWW-Authenticate") token: String
    ){
        checkUserIsBanned(token)
        val user = getUser(token)
        commentService.addComment(id, user.id, data.text)
    }

    @PostMapping("/comments/{id}/edit")
    fun editComment(
            @PathVariable id: UUID,
            @RequestBody data: PubComData,
            @RequestHeader(value = "WWW-Authenticate") token: String
    ){
        checkUserIsBanned(token)
        val comment = commentService.getCommentByID(id)
        val user = getUser(token)
        if (comment.user != user) throw ForbiddenException()
        commentService.editComment(id, data.text)
    }

    @PostMapping("/comments/{id}/delete")
    fun deleteComment(
            @PathVariable id: UUID,
            @RequestHeader(value = "WWW-Authenticate") token: String
    ){
        val comment = commentService.getCommentByID(id)
        val user = getUser(token)
        if (comment.user != user && !user.isPrivileged()) throw ForbiddenException()
        commentService.deleteComment(id)
    }
}