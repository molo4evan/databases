package ru.nsu.ccfit.molochev.databases.fitwikibackend.repos

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.query.Procedure
import org.springframework.data.repository.query.Param
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Article
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Section
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.User
import java.util.*
import javax.persistence.NamedNativeQuery

interface ArticleRepository: JpaRepository<Article, UUID> {
    fun findAllBySectionAndPublishedIsTrue(section: Section): List<Article>

    fun findAllByAuthorAndPublishedIsTrue(author: User): List<Article>

    fun findAllByPublishedIsFalse(): List<Article>

    fun findTop30ByPublishedIsTrueOrderByRatingDesc(): List<Article>

    fun findTopByPublishedIsTrueOrderByRatingDesc(pageable: Pageable): Slice<Article>

    @Query("UPDATE article SET previous_version =: new_ver WHERE previous_version =: old_ver")
    fun updatePreviousVersion(@Param("old_ver") oldVersion: UUID, @Param("new_ver") newVersion: UUID)

    fun deleteAllByPreviousVersion(previousVersion: UUID)

    @Query("SELECT search_articles()", nativeQuery = true)
    fun searchArticles
}