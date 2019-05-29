package ru.nsu.ccfit.android.fitwiki.view.viewsection

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.nsu.ccfit.android.fitwiki.R
import ru.nsu.ccfit.android.fitwiki.control.ControllerHolder
import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.view.base.BaseActivity
import ru.nsu.ccfit.android.fitwiki.view.viewarticle.ViewArticleActivity
import java.text.SimpleDateFormat

class ViewSectionActivity : BaseActivity(), IViewSectionView {
    private lateinit var presenter: IViewSectionPresenter
    private lateinit var recyclerView: RecyclerView
    private val activity = this

    companion object {
        const val EXTRA_SECTION_NAME = "EXTRA_SECTION_NAME"
    }

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_section)

        val sectionName = intent.getStringExtra(EXTRA_SECTION_NAME)

        val presenter = ViewSectionPresenter(this, sectionName)
        presenter.setControllers(ControllerHolder.controllers)
        setPresenter(presenter)

        recyclerView = findViewById(R.id.section_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }
    //endregion

    //region IViewSectionView
    override fun openArticle(articleID: String, sectionName: String) {
        val intent = Intent(this, ViewArticleActivity::class.java)
        intent.putExtra(ViewArticleActivity.EXTRA_ARTICLE_ID, articleID)
        intent.putExtra(ViewArticleActivity.EXTRA_SECTION_NAME, sectionName)
        startActivity(intent)
    }

    override fun setPresenter(presenter: IViewSectionPresenter) {
        this.presenter = presenter
    }

    override fun showSection(section: List<Article>) {
        recyclerView.adapter = SectionAdapter(section)
    }

    override fun showErrorScreen() {
        setContentView(R.layout.error_message)
        val text = findViewById<TextView>(R.id.error_text)
        text.text = getString(R.string.error_view_section)
    }
    //endregion

    //region Holder
    private inner class SectionHolder(inflater: LayoutInflater, container: ViewGroup):
            RecyclerView.ViewHolder(inflater.inflate(R.layout.item_article, container, false)),
            View.OnClickListener {
        private lateinit var article: Article
        private val titleTextView: TextView = itemView.findViewById(R.id.article_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.publication_date)
        private val ratingTextView: TextView = itemView.findViewById(R.id.rating)
        private val summaryTextView: TextView = itemView.findViewById(R.id.article_summary)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(article: Article){
            this.article = article
            titleTextView.text = article.title
            val dateString = SimpleDateFormat("yyyy.MM.dd").format(article.date)
            dateTextView.text = dateString
            ratingTextView.text = article.rating.toString()
            summaryTextView.text = article.summary ?:
                    if (article.text.length - Article.DEFAULT_SUMMARY_SIZE > 0) {
                        article.text.dropLast(article.text.length - Article.DEFAULT_SUMMARY_SIZE)
                    } else {
                        article.text
                    }

        }

        override fun onClick(view: View?) {
            presenter.onArticleSelected(article)
        }
    }
    //endregion

    //region Adapter
    private inner class SectionAdapter(private val articles: List<Article>): RecyclerView.Adapter<SectionHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): SectionHolder {
            val inflater = LayoutInflater.from(activity)
            return SectionHolder(inflater, parent)
        }

        override fun getItemCount() = articles.size

        override fun onBindViewHolder(holder: SectionHolder, position: Int) {
            val article = articles[position]
            holder.bind(article)
        }
    }
    //endregion
}
