package ru.nsu.ccfit.android.fitwiki.view.viewoffered

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_view_offered.*
import ru.nsu.ccfit.android.fitwiki.R
import ru.nsu.ccfit.android.fitwiki.control.ControllerHolder
import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.view.base.BaseActivity

class ViewOfferedActivity: BaseActivity(), IViewOfferedView {
    companion object {
        const val EXTRA_OFFERED_ID = "EXTRA_OFFERED_ID"
    }

    private lateinit var presenter: IViewOfferedPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_offered)

        val id = intent.getStringExtra(EXTRA_OFFERED_ID)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        title = getString(R.string.offered_article_title)

        val presenter = ViewOfferedPresenter(this, id)
        presenter.setControllers(ControllerHolder.controllers)
        setPresenter(presenter)

        publish.setOnClickListener {
            presenter.confirm()
        }
        cancel.setOnClickListener {
            presenter.cancel()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setPresenter(presenter: IViewOfferedPresenter) {
        this.presenter = presenter
    }

    override fun showArticle(article: Article, authorName: String) {
        val name = findViewById<TextView>(R.id.article_title)
        val section = findViewById<TextView>(R.id.article_section)
        val author = findViewById<TextView>(R.id.article_author)
        val summary = findViewById<TextView>(R.id.article_summary)
        val text = findViewById<TextView>(R.id.artice_text)
        name.text = article.title
        section.text = article.sectionName
        author.text = authorName
        summary.text = article.summary
        text.text = article.text
    }

    override fun dispose() {
        finish()
    }
}