package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.offered

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import ru.nsu.ccfit.android.fitwiki.R
import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.view.navigation.FeedFragment
import ru.nsu.ccfit.android.fitwiki.view.viewoffered.ViewOfferedActivity
import java.text.SimpleDateFormat

class OfferedFragment: FeedFragment(), IOfferedView {
        private lateinit var presenter: IOfferedPresenter

    override fun setPresenter(presenter: IOfferedPresenter) {
        this.presenter = presenter
    }

    override fun showOffered(articles: List<Article>) {
        recyclerView.adapter = OfferedAdapter(articles)
    }

    override fun openOfferedArticle(articleID: String) {
        val intent = Intent(activity, ViewOfferedActivity::class.java)
        intent.putExtra(ViewOfferedActivity.EXTRA_OFFERED_ID, articleID)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    //region Holder
    private inner class OfferedHolder(inflater: LayoutInflater, container: ViewGroup):
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
    private inner class OfferedAdapter(private val articles: List<Article>): RecyclerView.Adapter<OfferedHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): OfferedHolder {
            val inflater = LayoutInflater.from(activity)
            return OfferedHolder(inflater, parent)
        }

        override fun getItemCount() = articles.size

        override fun onBindViewHolder(holder: OfferedHolder, position: Int) {
            val article = articles[position]
            holder.bind(article)
        }
    }
    //endregion
}