package ru.nsu.ccfit.android.fitwiki.common.netstub

import ru.nsu.ccfit.android.fitwiki.model.Article

object FakeArticlesRepo {

    val sections = mutableMapOf<String, MutableMap<String, Article>>()

    val lastArticles: List<Article>

    init {
        sections["Subjects"] = mutableMapOf()
        var article = Article(title = "Английский", text = "Статья про английский язык", authorId = "0", sectionName = "Subjects")
        sections["Subjects"]!![article.id] = article
        article = Article(title = "ООАиД", text = "Статья про объектно-ориентированный анализ и дизайн", authorId = "1", sectionName = "Subjects")
        sections["Subjects"]!![article.id] = article
        article = Article(title = "Физическая культура", text = "Статья про физическую культуру", authorId = "0", sectionName = "Subjects", rating = 1)
        sections["Subjects"]!![article.id] = article

        sections["Преподаватели"] = mutableMapOf()
        article = Article(title = "Перепелкин", text = "Cтатья про Перепелкина", authorId = "2", sectionName = "Преподаватели")
        sections["Преподаватели"]!![article.id] = article
        article = Article(title = "Букшев", text = "Sometext", authorId = "1", sectionName = "Преподаватели")
        sections["Преподаватели"]!![article.id] = article
        article = Article(title = "Sometext", text = "SometextSometextSometextSometextSometextSomet" +
                "extSometextSometextSometextSometextSometextSometextSometextSometextSo" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext" +
                "metextSometextSometextSometextSometextSometextSometextSometextSometext", authorId = "0", sectionName = "Преподаватели")
        sections["Преподаватели"]!![article.id] = article

        sections["Some section"] = mutableMapOf()
        article = Article(title = "Article name 1", text = "Article text 1", authorId = "0", sectionName = "Some section")
        sections["Some section"]!![article.id] = article
        article = Article(title = "Article name 2", text = "Article text 2", authorId = "1", sectionName = "Some section")
        sections["Some section"]!![article.id] = article
        article = Article(title = "Article name 3", text = "Article text 3", authorId = "2", sectionName = "Some section")
        sections["Some section"]!![article.id] = article
        article = Article(title = "Article name 4", text = "Article text 4", authorId = "3", sectionName = "Some section")
        sections["Some section"]!![article.id] = article
        article = Article(title = "Article name 5", text = "Article text 5", authorId = "4", sectionName = "Some section", rating = 5)
        sections["Some section"]!![article.id] = article
        article = Article(title = "Article name 6", text = "Article text 6", authorId = "3", sectionName = "Some section")
        sections["Some section"]!![article.id] = article
        article = Article(title = "Article name 7", text = "Article text 7", authorId = "2", sectionName = "Some section")
        sections["Some section"]!![article.id] = article
        article = Article(title = "Article name 8", text = "Article text 8", authorId = "1", sectionName = "Some section")
        sections["Some section"]!![article.id] = article
        article = Article(title = "Article name 9", text = "Article text 9", authorId = "0", sectionName = "Some section", rating = 1)
        sections["Some section"]!![article.id] = article

        lastArticles = sections["Some section"]!!.values.toList()
    }
}