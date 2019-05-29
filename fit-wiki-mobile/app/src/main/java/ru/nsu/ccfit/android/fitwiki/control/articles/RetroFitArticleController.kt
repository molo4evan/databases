package ru.nsu.ccfit.android.fitwiki.control.articles

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.nsu.ccfit.android.fitwiki.App
import ru.nsu.ccfit.android.fitwiki.api.clientmodel.articles.ArticleData
import ru.nsu.ccfit.android.fitwiki.api.clientmodel.articles.PublishData
import ru.nsu.ccfit.android.fitwiki.api.clientmodel.articles.RateData
import ru.nsu.ccfit.android.fitwiki.api.clientmodel.sections.NewSectionData
import ru.nsu.ccfit.android.fitwiki.api.clientmodel.sections.SectionData
import ru.nsu.ccfit.android.fitwiki.model.Article
import java.util.*

class RetroFitArticleController: IArticleController {
    override fun loadSectionNames(callback: IArticleController.ILoadSectionNamesCallback) {
        App.api.getRootSections().enqueue(object : Callback<List<SectionData>> {
            override fun onFailure(call: Call<List<SectionData>>, t: Throwable) {
                callback.onSectionNamesLoaded(listOf())
            }

            override fun onResponse(call: Call<List<SectionData>>, response: Response<List<SectionData>>) {
                if (response.body() == null){
                    callback.onSectionNamesLoaded(listOf())
                } else {
                    val names = response.body()!!
                    val nn = mutableListOf<String>()
                    for (name in names) {
                        nn.add(name.name)
                    }
                    callback.onSectionNamesLoaded(nn)
                }
            }

        })
    }

    override fun loadSection(sectionName: String, callback: IArticleController.ILoadSectionCallback) {
        App.api.getSectionByName(sectionName).enqueue(object : Callback<SectionData> {
            override fun onFailure(call: Call<SectionData>, t: Throwable) {
                callback.onSectionLoaded(null)
            }

            override fun onResponse(call: Call<SectionData>, response: Response<SectionData>) {
                if (response.body() == null){
                    callback.onSectionLoaded(null)
                } else {
                    val sec = response.body()!!
                    App.api.getArticlesFromSection(sec.id).enqueue(object : Callback<List<ArticleData>> {
                        override fun onFailure(call: Call<List<ArticleData>>, t: Throwable) {
                            callback.onSectionLoaded(null)
                        }

                        override fun onResponse(call: Call<List<ArticleData>>, response: Response<List<ArticleData>>) {
                            if (response.body() == null){
                                callback.onSectionLoaded(null)
                            } else {
                                val arts = response.body()!!
                                val a = mutableListOf<Article>()
                                for (art in arts) {
                                    a.add(art.toArticle())
                                }
                                callback.onSectionLoaded(a)
                            }
                        }
                    })
                }
            }
        })
    }

    override fun loadArticle(sectionName: String, articleID: String, callback: IArticleController.ILoadArticleCallback) {
        App.api.getArticle(UUID.fromString(articleID)).enqueue(object : Callback<ArticleData> {
            override fun onFailure(call: Call<ArticleData>, t: Throwable) {
                callback.onArticleLoaded(null)
            }

            override fun onResponse(call: Call<ArticleData>, response: Response<ArticleData>) {
                if (response.body() == null) callback.onArticleLoaded(null)
                else {
                    callback.onArticleLoaded(response.body()!!.toArticle())
                }
            }
        })
    }

    override fun loadRecent(callback: IArticleController.ILoadRecentCallback) {
        App.api.getBest30Articles().enqueue(object : Callback<List<ArticleData>> {
            override fun onFailure(call: Call<List<ArticleData>>, t: Throwable) {
                callback.onRecentLoaded(listOf())
            }

            override fun onResponse(call: Call<List<ArticleData>>, response: Response<List<ArticleData>>) {
                val ololo = response.body()
                if (response.body() == null) callback.onRecentLoaded(listOf())
                else {
                    val rec = response.body()!!
                    val recent = mutableListOf<Article>()
                    for (r in rec) {
                        recent.add(r.toArticle())
                    }
                    callback.onRecentLoaded(recent)
                }
            }
        })
    }

    override fun loadOfferedArticles(callback: IArticleController.ILoadOfferedArticlesCallback) {
        if (App.userToken != null) {
            App.api.getOfferedArticles(App.userToken!!).enqueue(object : Callback<List<ArticleData>> {
                override fun onFailure(call: Call<List<ArticleData>>, t: Throwable) {
                    callback.onOfferedArticlesLoaded(listOf())
                }

                override fun onResponse(call: Call<List<ArticleData>>, response: Response<List<ArticleData>>) {
                    if (response.body() == null) callback.onOfferedArticlesLoaded(listOf())
                    else {
                        val of = response.body()!!
                        val offered = mutableListOf<Article>()
                        for (o in of) {
                            offered.add(o.toArticle())
                        }
                        callback.onOfferedArticlesLoaded(offered)
                    }
                }
            })
        } else {
            callback.onOfferedArticlesLoaded(listOf())
        }
    }

    override fun loadOfferedArticle(articleID: String, callback: IArticleController.ILoadOfferedArticleCallback) {
        if (App.userToken == null) callback.onOfferedArticleLoaded(null)
        else {
            App.api.getOfferedArticle(App.userToken!!, UUID.fromString(articleID)).enqueue(object : Callback<ArticleData> {
                override fun onFailure(call: Call<ArticleData>, t: Throwable) {
                    callback.onOfferedArticleLoaded(null)
                }

                override fun onResponse(call: Call<ArticleData>, response: Response<ArticleData>) {
                    if (response.body() == null) callback.onOfferedArticleLoaded(null)
                    else callback.onOfferedArticleLoaded(response.body()!!.toArticle())
                }
            })
        }
    }

    override fun loadUserArticles(userID: String, callback: IArticleController.ILoadUserArticlesCallback) {
        App.api.getArticlesOfUser(UUID.fromString(userID)).enqueue(object : Callback<List<ArticleData>> {
            override fun onFailure(call: Call<List<ArticleData>>, t: Throwable) {
                callback.onArticlesLoaded(listOf())
            }

            override fun onResponse(call: Call<List<ArticleData>>, response: Response<List<ArticleData>>) {
                if (response.body() == null){
                    callback.onArticlesLoaded(listOf())
                } else {
                    val arts = response.body()!!
                    val a = mutableListOf<Article>()
                    for (art in arts) {
                        a.add(art.toArticle())
                    }
                    callback.onArticlesLoaded(a)
                }
            }
        })
    }

    override fun createOfferedArticle(articleTitle: String, articleText: String, authorID: String?, sectionName: String, articleSummary: String?, callback: IArticleController.ICreateArticleCallback) {
        if (App.userToken == null) callback.onArticleCreated(null)
        App.api.getSectionByName(sectionName).enqueue(object : Callback<SectionData> {
            override fun onFailure(call: Call<SectionData>, t: Throwable) {
                callback.onArticleCreated(null)
            }

            override fun onResponse(call: Call<SectionData>, response: Response<SectionData>) {
                if (response.body() == null) callback.onArticleCreated(null)
                else {
                    val p = PublishData(articleTitle, articleText, articleSummary, response.body()!!.id)
                    App.api.offerNewArticle(p, App.userToken!!).enqueue(object : Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            callback.onArticleCreated(null)
                        }

                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            callback.onArticleCreated(Article())
                        }
                    })
                }
            }
        })
    }

    override fun editArticle(newState: Article, callback: IArticleController.IEditArticleCallback) {
        if (App.userToken == null) callback.onArticleEdited(false)
        App.api.getSectionByName(newState.sectionName).enqueue(object : Callback<SectionData> {
            override fun onFailure(call: Call<SectionData>, t: Throwable) {
                callback.onArticleEdited(false)
            }

            override fun onResponse(call: Call<SectionData>, response: Response<SectionData>) {
                if (response.body() == null) callback.onArticleEdited(false)
                else {
                    val p = PublishData(newState.title, newState.text, newState.summary, response.body()!!.id)
                    App.api.offerArticleChanges(UUID.fromString(newState.id), p, App.userToken!!).enqueue(object : Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            callback.onArticleEdited(false)
                        }

                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            callback.onArticleEdited(true)
                        }
                    })
                }
            }
        })
    }

    override fun publishOfferedArticle(articleID: String, callback: IArticleController.IPublishOfferedArticleCallback) {
        if (App.userToken == null) return
        App.api.publishOfferedArticle(UUID.fromString(articleID), App.userToken!!).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                callback.onOfferedArticlePublished()
            }
        })
    }

    override fun cancelOfferedArticle(articleID: String, callback: IArticleController.ICancelOfferedArticleCallback) {
        if (App.userToken == null) return
        App.api.declineOfferedArticle(UUID.fromString(articleID), App.userToken!!).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                callback.onOfferedArticleCancelled()
            }
        })
    }

    override fun addSection(sectionName: String, callback: IArticleController.IAddSectionCallback) {
        if (App.userToken == null) return
        App.api.addSection(App.userToken!!, NewSectionData(sectionName, null)).enqueue(object : Callback<SectionData> {
            override fun onFailure(call: Call<SectionData>, t: Throwable) {
                callback.onSectionAdded(listOf())
            }

            override fun onResponse(call: Call<SectionData>, response: Response<SectionData>) {
                callback.onSectionAdded(listOf())
            }
        })
    }

    override fun updateArticleRating(articleID: String, increase: Boolean, callback: IArticleController.IUpdateArticleRatingCallback) {
        if (App.userToken == null) return
        App.api.rateArticle(UUID.fromString(articleID), RateData(increase), App.userToken!!).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                callback.onArticleRatingUpdated(0)
            }
        })
    }
}