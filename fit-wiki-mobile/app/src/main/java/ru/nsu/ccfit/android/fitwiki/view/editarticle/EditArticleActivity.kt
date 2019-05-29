package ru.nsu.ccfit.android.fitwiki.view.editarticle

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import ru.nsu.ccfit.android.fitwiki.R
import ru.nsu.ccfit.android.fitwiki.control.ControllerHolder
import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.view.base.BaseActivity

class EditArticleActivity: BaseActivity(), IEditArticleView {
    companion object {
        const val ARTICLE_ID_EXTRA = "ARTICLE_ID_EXTRA"
        const val SECTION_NAME_EXTRA = "SECTION_NAME_EXTRA"
    }

    private lateinit var presenter: IEditArticlePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_article)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        title = getString(R.string.edit_article_title)

        val id = intent.getStringExtra(ARTICLE_ID_EXTRA)
        val section = intent.getStringExtra(SECTION_NAME_EXTRA)
        val presenter = EditArticlePresenter(this, id, section)
        presenter.setControllers(ControllerHolder.controllers)
        setPresenter(presenter)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setPresenter(presenter: IEditArticlePresenter) {
        this.presenter = presenter
    }

    override fun showInfo(article: Article, authorName: String) {
        val name = findViewById<TextView>(R.id.name)
        val section = findViewById<TextView>(R.id.section)
        val author = findViewById<TextView>(R.id.author)
        val summary = findViewById<EditText>(R.id.summary)
        val text = findViewById<EditText>(R.id.text)

        name.text = article.title
        section.text = article.sectionName
        author.text = authorName
        summary.text.insert(0, article.summary ?: "")
        text.text.insert(0, article.text)

        val submit = findViewById<Button>(R.id.submit)
        submit.setOnClickListener {
            val newSummary = summary.text.toString()
            val newText = text.text.toString()
            val newArticle = Article(
                    article.id,
                    article.title,
                    newText,
                    article.authorId,
                    article.sectionName,
                    newSummary,
                    article.rating,
                    article.date
            )
            presenter.editArticle(newArticle)
        }
        val cancel = findViewById<Button>(R.id.cancel)
        cancel.setOnClickListener {
            dispose()
        }
    }

    override fun dispose() {
        finish()
    }
}