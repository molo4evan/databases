package ru.nsu.ccfit.android.fitwiki.model

import java.util.*

data class Article(
        val id: String = UUID.randomUUID().toString(),
        val title: String,
        val text: String,
        val authorId: String,
        val sectionName: String,
        val summary: String? = null,
        val rating: Int = 0,
        val date: Date = Date()
) {
    companion object {
        const val DEFAULT_SUMMARY_SIZE = 50
    }

    constructor(): this(
           "",
            "",
            "",
            "",
            ""
    )
}