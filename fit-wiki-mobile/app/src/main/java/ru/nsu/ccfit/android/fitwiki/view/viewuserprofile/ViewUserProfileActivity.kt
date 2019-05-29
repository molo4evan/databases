package ru.nsu.ccfit.android.fitwiki.view.viewuserprofile

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import ru.nsu.ccfit.android.fitwiki.R
import ru.nsu.ccfit.android.fitwiki.control.ControllerHolder
import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.model.UserInfo
import ru.nsu.ccfit.android.fitwiki.view.base.BaseActivity
import ru.nsu.ccfit.android.fitwiki.view.viewarticle.ViewArticleActivity
import java.text.SimpleDateFormat
import java.util.*

class ViewUserProfileActivity : BaseActivity(), IViewUserProfileView {
    private lateinit var presenter: IViewUserProfilePresenter
    private lateinit var recyclerView: RecyclerView
    private val activity = this

    private var userBanned = false
    private var userModer = false
    private var userAdmin = false
    private var canBan = false
    private var canModer = false

    companion object {
        const val EXTRA_USER_ID = "EXTRA_USER_ID"
    }

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile)

        val userID = intent.getStringExtra(EXTRA_USER_ID)

        val presenter = ViewUserProfilePresenter(this, userID)
        presenter.setControllers(ControllerHolder.controllers)
        setPresenter(presenter)

        recyclerView = findViewById(R.id.user_articles_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        title = getString(R.string.user_profile_title)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }
    //endregion

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_profile_menu, menu)
        setupMenu()
        presenter.loadUser()
        presenter.loadMyself()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_banned -> {
                if (item.isChecked) {
                    presenter.onSetBanned(false)
                } else {
                    presenter.onSetBanned(true)
                }
            }
            R.id.action_moderator -> {
                if (item.isChecked) {
                    presenter.onSetModerator(false)
                } else {
                    presenter.onSetModerator(true)
                }
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun setupMenu(){
        val menu = findViewById<Toolbar>(R.id.toolbar).menu
        val banned = menu.findItem(R.id.action_banned)
        val moderator = menu.findItem(R.id.action_moderator)

        if (banned != null && moderator != null){
            banned.isVisible = canBan && !userModer && !userAdmin
            banned.isChecked = userBanned
            moderator.isVisible = canModer && !userBanned && !userAdmin
            moderator.isChecked = userModer
        }
    }

    //region IVewUserProfileView
    override fun showProfile(user: UserInfo, articles: List<Article>) {
        recyclerView.adapter = UserArticlesAdapter(articles)

        updateUser(user)

    }

    override fun showErrorScreen() {
        setContentView(R.layout.error_message)
        val text = findViewById<TextView>(R.id.error_text)
        text.text = getString(R.string.error_view_user_profile)
    }

    override fun openArticle(article: Article) {
        val intent = Intent(this, ViewArticleActivity::class.java)
        intent.putExtra(ViewArticleActivity.EXTRA_ARTICLE_ID, article.id)
        intent.putExtra(ViewArticleActivity.EXTRA_SECTION_NAME, article.sectionName)
        startActivity(intent)
    }

    override fun updateMyPermission(myself: UserInfo?) {
        if (myself == null){
            canBan = false
            canModer = false
        } else {
            canBan = myself.isModerator || myself.isAdmin
            canModer = myself.isAdmin
        }
        setupMenu()
    }

    private fun updateUser(user: UserInfo) {
        val userPhoto = findViewById<ImageView>(R.id.user_photo)
        val username = findViewById<TextView>(R.id.username)
        val registered = findViewById<TextView>(R.id.registered_since)
        val rating = findViewById<TextView>(R.id.rating)
        val role = findViewById<TextView>(R.id.role)

        userBanned = user.isBanned
        userModer = user.isModerator
        userAdmin = user.isAdmin

        if (user.photoUrl != null) {
            Glide
                    .with(this)
                    .load(user.photoUrl)
                    .into(userPhoto)
        }

        username.text = user.username
        registered.text = getString(R.string.register_date_placeholder) + " " +
                SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH).format(user.registrationDate)
        rating.text = user.rating.toString()
        role.text = if (user.isAdmin) {
            getString(R.string.administrator_placeholder)
        } else {
            if (user.isBanned) {
                getString(R.string.banned_placeholder)
            } else {
                if (user.isModerator) {
                    getString(R.string.moderator_placeholder)
                } else {
                    getString(R.string.common_placeholder)
                }
            }
        }
    }

    override fun cannotBan() {
        Toast.makeText(this, "Cannot ban", Toast.LENGTH_SHORT).show()
    }

    override fun setPresenter(presenter: IViewUserProfilePresenter) {
        this.presenter = presenter
    }
    //endregion

    //region Holder
    private inner class UserArticlesHolder(inflater: LayoutInflater, container: ViewGroup):
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
            val dateString = SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH).
                    format(article.date)
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
    private inner class UserArticlesAdapter(private val articles: List<Article>):
            RecyclerView.Adapter<UserArticlesHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): UserArticlesHolder {
            val inflater = LayoutInflater.from(activity)
            return UserArticlesHolder(inflater, parent)
        }

        override fun getItemCount() = articles.size

        override fun onBindViewHolder(holder: UserArticlesHolder, position: Int) {
            val article = articles[position]
            holder.bind(article)
        }
    }
    //endregion
}
