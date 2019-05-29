package ru.nsu.ccfit.android.fitwiki.control.articles

import ru.nsu.ccfit.android.fitwiki.control.IController
import ru.nsu.ccfit.android.fitwiki.model.Article

interface IArticleController: IController {
    interface ILoadSectionNamesCallback {
        fun onSectionNamesLoaded(sectionNames: List<String>)
    }

    interface ILoadSectionCallback {
        fun onSectionLoaded(section: List<Article>?)
    }

    interface ILoadArticleCallback {
        fun onArticleLoaded(article: Article?)
    }

    interface ILoadRecentCallback {
        fun onRecentLoaded(articles: List<Article>)
    }

    interface ILoadOfferedArticlesCallback {
        fun onOfferedArticlesLoaded(articles: List<Article>)
    }

    interface ILoadOfferedArticleCallback {
        fun onOfferedArticleLoaded(article: Article?)
    }

    interface ILoadUserArticlesCallback {
        fun onArticlesLoaded(articles: List<Article>)
    }

    interface ICreateArticleCallback {
        fun onArticleCreated(article: Article?)
    }

    interface IEditArticleCallback{
        fun onArticleEdited(success: Boolean)
    }

    interface IPublishOfferedArticleCallback {
        fun onOfferedArticlePublished()
    }

    interface ICancelOfferedArticleCallback {
        fun onOfferedArticleCancelled()
    }

    interface IAddSectionCallback {
        fun onSectionAdded(updatedSectionList: List<String>)
    }

    interface IUpdateArticleRatingCallback {
        fun onArticleRatingUpdated(newRating: Int)
    }

    fun loadSectionNames(callback: ILoadSectionNamesCallback)

    fun loadSection(sectionName: String, callback: ILoadSectionCallback)

    fun loadArticle(sectionName: String, articleID: String, callback: ILoadArticleCallback)

    fun loadRecent(callback: ILoadRecentCallback)

    fun loadOfferedArticles(callback: ILoadOfferedArticlesCallback)

    fun loadOfferedArticle(articleID: String, callback: ILoadOfferedArticleCallback)

    fun loadUserArticles(userID: String, callback: ILoadUserArticlesCallback)

    fun createOfferedArticle(
            articleTitle: String,
            articleText: String,
            authorID: String?,
            sectionName: String,
            articleSummary: String?,
            callback: ICreateArticleCallback
    )

    fun editArticle(newState: Article, callback: IEditArticleCallback)

    fun publishOfferedArticle(articleID: String, callback: IPublishOfferedArticleCallback)

    fun cancelOfferedArticle(articleID: String, callback: ICancelOfferedArticleCallback)

    fun addSection(sectionName: String, callback: IAddSectionCallback)

    fun updateArticleRating(articleID: String, increase: Boolean, callback: IUpdateArticleRatingCallback)
}