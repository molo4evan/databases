package ru.nsu.ccfit.android.fitwiki.view.navigation

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nsu.ccfit.android.fitwiki.R
import ru.nsu.ccfit.android.fitwiki.view.base.BaseFragment

abstract class FeedFragment : BaseFragment(R.layout.fragment_feed) {
    protected lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        recyclerView = view!!.findViewById(R.id.feed_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        return view
    }
}
