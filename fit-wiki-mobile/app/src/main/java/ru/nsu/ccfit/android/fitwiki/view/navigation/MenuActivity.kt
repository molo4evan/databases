package ru.nsu.ccfit.android.fitwiki.view.navigation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.*
import kotlinx.android.synthetic.main.nav_header_authorized.view.*
import kotlinx.android.synthetic.main.nav_header_unauthorized.view.*
import ru.nsu.ccfit.android.fitwiki.R
import ru.nsu.ccfit.android.fitwiki.control.ControllerHolder
import ru.nsu.ccfit.android.fitwiki.model.UserInfo
import ru.nsu.ccfit.android.fitwiki.view.base.BaseActivity
import ru.nsu.ccfit.android.fitwiki.view.base.BaseFragment
import ru.nsu.ccfit.android.fitwiki.view.createarticle.CreateArticleActivity
import ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.backups.BackupsFragment
import ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.backups.BackupsPresenter
import ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.mainscreen.MainScreenFragment
import ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.mainscreen.MainScreenPresenter
import ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.offered.OfferedFragment
import ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.offered.OfferedPresenter
import ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.sections.SectionsFragment
import ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.sections.SectionsPresenter
import ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.users.UserListFragment
import ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.users.UserListPresenter
import ru.nsu.ccfit.android.fitwiki.view.signin.SignInActivity
import ru.nsu.ccfit.android.fitwiki.view.signup.SignUpActivity
import ru.nsu.ccfit.android.fitwiki.view.viewmyprofile.ViewMyProfileActivity

class MenuActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, IMenuView {
    companion object {
        private const val RC_SIGN_IN = 1337
        private const val ITEM_ID = "ITEM_ID"
    }

    private lateinit var presenter: IMenuPresenter

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val presenter = MenuPresenter(this)
        presenter.setControllers(ControllerHolder.controllers)
        setPresenter(presenter)

        setupMainScreen()
        setupMenu()
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(ITEM_ID, nav_view.checkedItem?.itemId ?: R.id.nav_main)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            chooseCurrentSection(savedInstanceState.getInt(ITEM_ID))
        }
    }
    //endregion

    //region IMenuView
    override fun setPresenter(presenter: IMenuPresenter) {
        this.presenter = presenter
    }

    override fun updateUI(user: UserInfo?){
        if (user != null) {
            nav_view.removeHeaderView(nav_view.getHeaderView(0))
            nav_view.inflateHeaderView(R.layout.nav_header_authorized)
            val header = nav_view.getHeaderView(0)

            Glide.with(this).load(user.photoUrl).into(header.user_photo)
            header.username.text = user.username
            header.role.text = when {
                user.isBanned -> getString(R.string.banned_placeholder)
                user.isModerator -> getString(R.string.moderator_placeholder)
                user.isAdmin -> getString(R.string.administrator_placeholder)
                else -> getString(R.string.common_placeholder)
            }

            header.setOnClickListener {
                val intent = Intent(this, ViewMyProfileActivity::class.java)
                startActivity(intent)
            }

            nav_view.menu.findItem(R.id.nav_users).isVisible =
                    user.isModerator || user.isAdmin
            nav_view.menu.findItem(R.id.nav_offered).isVisible =
                    user.isModerator || user.isAdmin
            nav_view.menu.findItem(R.id.nav_backups).isVisible =
                    user.isAdmin
            nav_view.menu.findItem(R.id.nav_new_article).isVisible =
                    !user.isBanned
            nav_view.menu.findItem(R.id.nav_logout).isVisible = true
        } else {
            nav_view.removeHeaderView(nav_view.getHeaderView(0))
            nav_view.inflateHeaderView(R.layout.nav_header_unauthorized)
            val header = nav_view.getHeaderView(0)

            nav_view.menu.findItem(R.id.nav_users).isVisible = false
            nav_view.menu.findItem(R.id.nav_offered).isVisible = false
            nav_view.menu.findItem(R.id.nav_backups).isVisible = false
            nav_view.menu.findItem(R.id.nav_new_article).isVisible = false
            nav_view.menu.findItem(R.id.nav_logout).isVisible = false

            header.sign_in.setOnClickListener {
                val intent = Intent(this, SignInActivity::class.java)
                startActivityForResult(intent, RC_SIGN_IN)
            }
            header.sign_up.setOnClickListener {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivityForResult(intent, RC_SIGN_IN)
            }
        }
    }
    //endregion

    //region Configuring
    private fun setupMenu(){
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        this.title = getString(R.string.main_screen_title)

        nav_view.menu.findItem(R.id.nav_main).isChecked = true
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN){
            if (resultCode == Activity.RESULT_OK){
                presenter.loadCurrentUser()
            }
        }
    }
    //endregion

    //region Navigation Drawer
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        chooseCurrentSection(item.itemId)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun chooseCurrentSection(id: Int) {
        when (id){
            R.id.nav_main -> {
                this.title = getString(R.string.main_screen_title)
                setupMainScreen()
            }
            R.id.nav_sections -> {
                this.title = getString(R.string.sections_title)
                setupSections()
            }
            R.id.nav_users -> {
                this.title = getString(R.string.users_title)
                setupUsers()
            }
            R.id.nav_offered -> {
                this.title = getString(R.string.offered_title)
                setupOffered()
            }
            R.id.nav_backups -> {
                this.title = getString(R.string.backups_title)
                setupBackups()
            }
            R.id.nav_new_article -> {
                val intent = Intent(this, CreateArticleActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_logout -> {
                presenter.logoutCurrentUser()
            }
            else -> {
                throw Exception()   //TODO: stub
            }
        }
    }
    //endregion

    //region Screens
    private fun setupMainScreen(){
        val fragment = MainScreenFragment()
        val presenter = MainScreenPresenter(fragment)
        presenter.setControllers(ControllerHolder.controllers)
        fragment.setPresenter(presenter)
        setFragment(fragment)
    }

    private fun setupSections(){
        val fragment = SectionsFragment()
        val presenter = SectionsPresenter(fragment)
        presenter.setControllers(ControllerHolder.controllers)
        fragment.setPresenter(presenter)
        setFragment(fragment)
    }

    private fun setupUsers(){
        val fragment = UserListFragment()
        val presenter = UserListPresenter(fragment)
        presenter.setControllers(ControllerHolder.controllers)
        fragment.setPresenter(presenter)
        setFragment(fragment)
    }

    private fun setupOffered(){
        val fragment = OfferedFragment()
        val presenter = OfferedPresenter(fragment)
        presenter.setControllers(ControllerHolder.controllers)
        fragment.setPresenter(presenter)
        setFragment(fragment)
    }

    private fun setupBackups(){
        val fragment = BackupsFragment()
        val presenter = BackupsPresenter(fragment)
        presenter.setControllers(ControllerHolder.controllers)
        fragment.setPresenter(presenter)
        setFragment(fragment)
    }
    //endregion

    private fun setFragment(fragment: BaseFragment){
        supportFragmentManager.
                beginTransaction().
                replace(R.id.fragment_container, fragment).
                commit()
    }
}
