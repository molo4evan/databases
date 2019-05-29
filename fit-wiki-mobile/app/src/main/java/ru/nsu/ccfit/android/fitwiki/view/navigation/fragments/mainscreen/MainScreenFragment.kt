package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.mainscreen

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.nsu.ccfit.android.fitwiki.R
import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.view.navigation.FeedFragment
import ru.nsu.ccfit.android.fitwiki.view.viewarticle.ViewArticleActivity
import java.text.SimpleDateFormat

class MainScreenFragment : FeedFragment(), IMainScreenView {
    private lateinit var presenter: IMainScreenPresenter

    //region IMainScreenView
    override fun setPresenter(presenter: IMainScreenPresenter) {
        this.presenter = presenter
    }

    override fun showRecent(articles: List<Article>) {
        recyclerView.adapter = RecentAdapter(articles)
    }

    override fun openArticleDetaiedScreeen(articleID: String, sectionName: String) {
        val intent = Intent(context, ViewArticleActivity::class.java)
        intent.putExtra(ViewArticleActivity.EXTRA_ARTICLE_ID, articleID)
        intent.putExtra(ViewArticleActivity.EXTRA_SECTION_NAME, sectionName)
        startActivity(intent)
    }
    //endregion

    //region Lifecycle
    override fun onResume() {
        super.onResume()
        presenter.start()
    }
    //endregion

    //region Holder
    private inner class RecentHolder(inflater: LayoutInflater, container: ViewGroup):
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
            presenter.onArticleChosen(article)
        }
    }
    //endregion

    //region Adapter
    private inner class RecentAdapter(private val articles: List<Article>): RecyclerView.Adapter<RecentHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecentHolder {
            val inflater = LayoutInflater.from(activity)
            return RecentHolder(inflater, parent)
        }

        override fun getItemCount() = articles.size

        override fun onBindViewHolder(holder: RecentHolder, position: Int) {
            val article = articles[position]
            holder.bind(article)
        }
    }
    //endregion
}
