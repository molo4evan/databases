package ru.nsu.ccfit.molochev.databases.fitwikibackend.control

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.nsu.ccfit.molochev.databases.fitwikibackend.clientmodel.ArticleData
import ru.nsu.ccfit.molochev.databases.fitwikibackend.clientmodel.CommentData
import ru.nsu.ccfit.molochev.databases.fitwikibackend.services.ArticleService
import ru.nsu.ccfit.molochev.databases.fitwikibackend.services.CommentService
import java.util.*

@RestController
@RequestMapping("articles")
class ArticleController: CheckingController() {
    @Autowired
    private lateinit var articleService: ArticleService
    @Autowired
    private lateinit var commentService: CommentService

    @GetMapping("best30")
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

    @GetMapping("")
    fun getArticlesFromSection(@RequestParam section: UUID): List<ArticleData>{
        return articleService.getArticlesInSection(section).map(::ArticleData)
    }

    @GetMapping("")
    fun getArticlesOfUser(@RequestParam user: UUID): List<ArticleData> {
        return articleService.getArticlesOfUser(user).map(::ArticleData)
    }

    @GetMapping("/find")
    fun findArticles(@RequestParam text: String): List<ArticleData> {
        return articleService.searchArticles(text).map(::ArticleData)
    }

    @GetMapping("/{id}/comments")
    fun getArticleComments(@PathVariable id: UUID): List<CommentData> {
        return commentService.getArticleComments(id).map(::CommentData)
    }
}