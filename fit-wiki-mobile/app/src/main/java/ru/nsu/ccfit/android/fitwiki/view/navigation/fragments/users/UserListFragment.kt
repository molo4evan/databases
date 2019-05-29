package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.users

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.nsu.ccfit.android.fitwiki.R
import ru.nsu.ccfit.android.fitwiki.model.UserInfo
import ru.nsu.ccfit.android.fitwiki.view.navigation.FeedFragment
import ru.nsu.ccfit.android.fitwiki.view.viewuserprofile.ViewUserProfileActivity

class UserListFragment: FeedFragment(), IUserListView {
    private lateinit var presenter: IUserListPresenter

    //region IUserListView
    override fun setPresenter(presenter: IUserListPresenter) {
        this.presenter = presenter
    }

    override fun showUsers(users: List<UserInfo>) {
        recyclerView.adapter = UserAdapter(users)
    }

    override fun openUserProfile(userID: String) {
        val intent = Intent(activity, ViewUserProfileActivity::class.java)
        intent.putExtra(ViewUserProfileActivity.EXTRA_USER_ID, userID)
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
    private inner class UserHolder(inflater: LayoutInflater, container: ViewGroup):
            RecyclerView.ViewHolder(inflater.inflate(R.layout.item_user, container, false)),
            View.OnClickListener {
        private lateinit var user: UserInfo
        private val usernameTextView: TextView = itemView.findViewById(R.id.username)
        private val roleTextView: TextView = itemView.findViewById(R.id.role)
        private val ratingTextView: TextView = itemView.findViewById(R.id.rating)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(user: UserInfo){
            this.user = user

            usernameTextView.text = user.username

            roleTextView.text = if (user.isAdmin) {
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

            ratingTextView.text = user.rating.toString()
        }

        override fun onClick(view: View?) {
            presenter.onUserSelected(user)
        }
    }
    //endregion

    //region Adapter
    private inner class UserAdapter(private val users: List<UserInfo>): RecyclerView.Adapter<UserHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): UserHolder {
            val inflater = LayoutInflater.from(activity)
            return UserHolder(inflater, parent)
        }

        override fun getItemCount() = users.size

        override fun onBindViewHolder(holder: UserHolder, position: Int) {
            val user = users[position]
            holder.bind(user)
        }
    }
    //endregion
}