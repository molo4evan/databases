package ru.nsu.ccfit.android.fitwiki.view.viewarticle

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import ru.nsu.ccfit.android.fitwiki.R
import ru.nsu.ccfit.android.fitwiki.control.ControllerHolder
import ru.nsu.ccfit.android.fitwiki.model.Article
import ru.nsu.ccfit.android.fitwiki.model.UserInfo
import ru.nsu.ccfit.android.fitwiki.view.base.BaseActivity
import ru.nsu.ccfit.android.fitwiki.view.editarticle.EditArticleActivity
import ru.nsu.ccfit.android.fitwiki.view.viewuserprofile.ViewUserProfileActivity

class ViewArticleActivity : BaseActivity(), IViewArticleView {      //TODO: add rating changes and edit
    companion object {
        const val EXTRA_ARTICLE_ID = "EXTRA_ARTICLE_ID"
        const val EXTRA_SECTION_NAME = "EXTRA_SECTION_NAME"
    }

    private lateinit var presenter: IViewArticlePresenter

    private lateinit var article: Article
    private lateinit var user: UserInfo

    private var editVisible = false

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_article)

        val articleID = intent.getStringExtra(EXTRA_ARTICLE_ID)
        val sectionName = intent.getStringExtra(EXTRA_SECTION_NAME)

        val presenter = ViewArticlePresenter(this, articleID, sectionName)
        presenter.setControllers(ControllerHolder.controllers)
        setPresenter(presenter)

        val userProfile = findViewById<TextView>(R.id.article_author)
        userProfile.setOnClickListener {
            presenter.onUserProfileSelected()
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        title = getString(R.string.article_title)

        val inc = findViewById<ImageView>(R.id.increase_rating)
        inc.setOnClickListener {
            presenter.onIncreaseArticleRating()
        }
        inc.visibility = View.GONE
        val dec = findViewById<ImageView>(R.id.decrease_rating)
        dec.setOnClickListener {
            presenter.onDecreaseArticleRating()
        }
        dec.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }
    //endregion

    //region Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.editable_menu, menu)
        val edit = findViewById<Toolbar>(R.id.toolbar).menu.findItem(R.id.action_edit)
        edit.isVisible = editVisible
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId){
            R.id.action_edit -> {
                val intent = Intent(this, EditArticleActivity::class.java)
                intent.putExtra(EditArticleActivity.ARTICLE_ID_EXTRA, article.id)
                intent.putExtra(EditArticleActivity.SECTION_NAME_EXTRA, article.sectionName)
                startActivity(intent)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
    //endregion

    //region IViewArticleView
    override fun setPresenter(presenter: IViewArticlePresenter) {
        this.presenter = presenter
    }

    override fun showArticle(article: Article, user: UserInfo) {
        this.article = article

        val title = findViewById<TextView>(R.id.article_title)
        title.text = article.title

        val rating = findViewById<TextView>(R.id.article_rating)
        rating.text = article.rating.toString()

        val author = findViewById<TextView>(R.id.article_author)
        author.text = user.username

        val text = findViewById<TextView>(R.id.artice_text)
        text.text = article.text

        this.user = user
    }

    override fun setupSettings(user: UserInfo?) {
        Toast.makeText(this, "set up", Toast.LENGTH_SHORT).show()
        val inc = findViewById<ImageView>(R.id.increase_rating)
        val dec = findViewById<ImageView>(R.id.decrease_rating)
        if (inc != null && dec != null){
            if (user == null){
                inc.visibility = View.GONE
                dec.visibility = View.GONE
                editVisible = false
            } else {
                inc.visibility = View.VISIBLE    //!user.isBanned    //TODO: mb this?
                dec.visibility = View.VISIBLE    //!user.isBanned
                editVisible = !user.isBanned
            }
        }
    }

    override fun showErrorScreen() {
        setContentView(R.layout.error_message)
        val text = findViewById<TextView>(R.id.error_text)
        text.text = getString(R.string.error_view_article)
    }

    override fun openUserProfile() {
        val intent = Intent(this, ViewUserProfileActivity::class.java)
        intent.putExtra(ViewUserProfileActivity.EXTRA_USER_ID, user.id)
        startActivity(intent)
    }
    //endregion
}
