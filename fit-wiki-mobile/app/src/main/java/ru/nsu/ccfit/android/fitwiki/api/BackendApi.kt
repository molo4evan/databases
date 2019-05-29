package ru.nsu.ccfit.android.fitwiki.api

import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import ru.nsu.ccfit.android.fitwiki.api.clientmodel.BackBackup
import ru.nsu.ccfit.android.fitwiki.api.clientmodel.articles.*
import ru.nsu.ccfit.android.fitwiki.api.clientmodel.sections.NewSectionData
import ru.nsu.ccfit.android.fitwiki.api.clientmodel.sections.SectionData
import ru.nsu.ccfit.android.fitwiki.api.clientmodel.users.*
import java.util.*

interface BackendApi {
    @GET("/articles/best30")
    fun getBest30Articles(): Call<List<ArticleData>>

    @GET("articles/best")
    fun getBestArticles(@Query("first") first: Int, @Query("size") size: Int): Call<List<ArticleData>>

    @GET("articles/offered")
    fun getOfferedArticles(@Header("WWW-Authenticate") token: String): Call<List<ArticleData>>

    @GET("articles/offered/{id}")
    fun getOfferedArticle(@Header("WWW-Authenticate") token: String, @Path("id") id: UUID): Call<ArticleData>

    @GET("/articles/section/{id}")
    fun getArticlesFromSection(@Path("id") sectionId: UUID): Call<List<ArticleData>>

    @GET("/articles/user/{id}")
    fun getArticlesOfUser(@Path("id") userId: UUID): Call<List<ArticleData>>

    @GET("/articles/{id}")
    fun getArticle(@Path("id") articleId: UUID): Call<ArticleData>

    @GET("/articles/find")
    fun findArticles(@Query("text") text: String): Call<List<ArticleData>>

    @GET("/articles/{id}/comments")
    fun getArticleComments(@Path("id") articleId: UUID): Call<List<CommentData>>

    @POST("articles/offer")
    fun offerNewArticle(
         @Body data: PublishData,
         @Header("WWW-Authenticate") token: String
    ): Call<ResponseBody>

    @POST("/articles/{id}/offer")
    fun offerArticleChanges(
            @Path("id") articleId: UUID,
            @Body data: PublishData,
            @Header("WWW-Authenticate") token: String
    ): Call<ResponseBody>

    @POST("/articles/{id}/publish")
    fun publishOfferedArticle(
            @Path("id") articleId: UUID,
            @Header("WWW-Authenticate") token: String
    ): Call<ResponseBody>

    @POST("/articles/{id}/decline")
    fun declineOfferedArticle(
            @Path("id") articleId: UUID,
            @Header("WWW-Authenticate") token: String
    ): Call<ResponseBody>

    @POST("/articles/{id}/delete")
    fun deleteArticle(
            @Path("id") articleId: UUID,
            @Header("WWW-Authenticate") token: String
    ): Call<ResponseBody>

    @POST("/articles/{id}/rate")
    fun rateArticle(
            @Path("id") articleId: UUID,
            @Body data: RateData,
            @Header("WWW-Authenticate") token: String
    ): Call<ResponseBody>

    @POST("/articles/{id}/comment")
    fun commentArticle(
            @Path("id") articleId: UUID,
            @Body data: PubComData,
            @Header("WWW-Authenticate") token: String
    ): Call<ResponseBody>

    @POST("/comments/{id}/edit")
    fun editComment(
            @Path("id") commentId: UUID,
            @Body data: PubComData,
            @Header("WWW-Authenticate") token: String
    ): Call<ResponseBody>

    @POST("/comments/{id}/delete")
    fun deleteComment(
            @Path("id") commentId: UUID,
            @Header("WWW-Authenticate") token: String
    ): Call<ResponseBody>

    @GET("/sections")
    fun getRootSections(): Call<List<SectionData>>

    @GET("/sections/{id}")
    fun getSection(@Path("id") sectionId: UUID): Call<SectionData>

    @GET("/sections/name/{name}")
    fun getSectionByName(@Path("name") name: String): Call<SectionData>

    @GET("/sections/{id}/subsections")
    fun getSubsections(@Path("id") sectionId: UUID): Call<List<SectionData>>

    @GET("/sections/{id}/parent")
    fun getParent(@Path("id") sectionId: UUID): Call<SectionData>

    @POST("/sections")
    fun addSection(
            @Header("WWW-Authenticate") token: String,
            @Body section: NewSectionData
    ): Call<SectionData>

    @POST("/sections/{id}")
    fun editSectionName(
            @Header("WWW-Authenticate") token: String,
            @Path("id") sectionId: UUID,
            @Body name: String
    ): Call<SectionData>

    @POST("/sections/{id}/delete")
    fun deleteSection(
            @Header("WWW-Authenticate") token: String,
            @Path("id") sectionId: UUID
    ): Call<ResponseBody>

    @GET("/backups")
    fun getBackups(@Header("WWW-Authenticate") token: String): Call<List<BackBackup>>

    @POST("/backups")
    fun backup(
            @Header("WWW-Authenticate") token: String,
            @Body name: String
    ): Call<BackBackup>

    @POST("/backups/restore/{id}")
    fun restore(
            @Header("WWW-Authenticate") token: String,
            @Path("id") backupId: UUID
    ): Call<ResponseBody>

    @GET("/users")
    fun getUsers(@Header("WWW-Authenticate") token: String): Call<List<UserData>>

    @GET("/users/{id}")
    fun getUser(@Path("id") userId: UUID): Call<UserData>

    @GET("/users/me")
    fun getMyself(@Header("WWW-Authenticate") token: String): Call<MyselfData>

    @POST("/users/register")
    fun register(@Body regData: RegistrationData): Call<LoginResponse>

    @POST("/users/login")
    fun login(@Body logData: LoginRequest): Call<LoginResponse>

    @POST("/users/logout")
    fun logout(@Header("WWW-Authenticate") token: String): Call<ResponseBody>

    @POST("/users/{id}/role")
    fun changeUserStatus(
            @Header("WWW-Authenticate") token: String,
            @Path("id") userId: UUID,
            @Body newStatus: UserData.Role
    ): Call<UserData>

    @POST("/users/{id}/delete")
    fun deleteUser(
            @Header("WWW-Authenticate") token: String,
            @Path("id") userId: UUID
    ): Call<ResponseBody>

    @POST("/users/me")
    fun editMyself(
            @Header("WWW-Authenticate") token: String,
            @Body data: RegistrationData
    ): Call<UserData>
}
