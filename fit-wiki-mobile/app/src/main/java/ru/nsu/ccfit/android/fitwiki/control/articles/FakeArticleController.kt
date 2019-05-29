package ru.nsu.ccfit.android.fitwiki.control.articles
import ru.nsu.ccfit.android.fitwiki.common.netstub.FakeArticlesRepo
import ru.nsu.ccfit.android.fitwiki.model.Article
import java.util.*

class FakeArticleController: IArticleController {
    override fun editArticle(newState: Article, callback: IArticleController.IEditArticleCallback) {
        callback.onArticleEdited(true)
    }

    private val sections = FakeArticlesRepo.sections

    private val sectionNames = sections.keys.toList()

    override fun loadSectionNames(callback: IArticleController.ILoadSectionNamesCallback) {
        callback.onSectionNamesLoaded(sectionNames)
    }

    override fun loadSection(sectionName: String, callback: IArticleController.ILoadSectionCallback) {
        val articles = sections[sectionName]?.values?.toList()
        callback.onSectionLoaded(articles)
    }

    override fun loadArticle(sectionName: String, articleID: String, callback: IArticleController.ILoadArticleCallback) {
        val article = try {
            sections[sectionName]?.getValue(articleID)
        } catch (ex: NoSuchElementException) {
            null
        }
        callback.onArticleLoaded(article)
    }

    override fun loadRecent(callback: IArticleController.ILoadRecentCallback) {
        callback.onRecentLoaded(FakeArticlesRepo.lastArticles)
    }

    override fun loadOfferedArticles(callback: IArticleController.ILoadOfferedArticlesCallback) {
        callback.onOfferedArticlesLoaded(FakeArticlesRepo.lastArticles)
    }

    override fun loadOfferedArticle(articleID: String, callback: IArticleController.ILoadOfferedArticleCallback) {
        var article: Article? = null
        for (offered in FakeArticlesRepo.lastArticles) {
            if (offered.id.toString() == articleID) {
                article = offered
            }
        }
        callback.onOfferedArticleLoaded(article)
    }

    override fun createOfferedArticle(
            articleTitle: String,
            articleText: String,
            authorID: String?,
            sectionName: String,
            articleSummary: String?,
            callback: IArticleController.ICreateArticleCallback
    ) {
        callback.onArticleCreated(Article(title = articleTitle, text = articleText, authorId = authorID ?: UUID.randomUUID().toString(), sectionName = sectionName, summary = articleSummary))
    }

    override fun publishOfferedArticle(articleID: String, callback: IArticleController.IPublishOfferedArticleCallback) {
        callback.onOfferedArticlePublished()
    }

    override fun cancelOfferedArticle(articleID: String, callback: IArticleController.ICancelOfferedArticleCallback) {
        callback.onOfferedArticleCancelled()
    }

    override fun addSection(sectionName: String, callback: IArticleController.IAddSectionCallback) {
        callback.onSectionAdded(sectionNames)
    }

    override fun loadUserArticles(userID: String, callback: IArticleController.ILoadUserArticlesCallback) {
        callback.onArticlesLoaded(FakeArticlesRepo.lastArticles)
    }

    override fun updateArticleRating(articleID: String, increase: Boolean, callback: IArticleController.IUpdateArticleRatingCallback) {
        callback.onArticleRatingUpdated(0)
    }
}