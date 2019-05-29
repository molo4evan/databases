package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.sections

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import ru.nsu.ccfit.android.fitwiki.R
import ru.nsu.ccfit.android.fitwiki.common.utils.Constants
import ru.nsu.ccfit.android.fitwiki.model.UserInfo
import ru.nsu.ccfit.android.fitwiki.view.base.BaseFragment
import ru.nsu.ccfit.android.fitwiki.view.viewsection.ViewSectionActivity

class SectionsFragment: BaseFragment(R.layout.fragment_view_add_elements), ISectionsView {
    private lateinit var recyclerView: RecyclerView
    private lateinit var presenter: ISectionsPresenter

    private lateinit var dialog: AlertDialog

    //region ISectionsView
    override fun setPresenter(presenter: ISectionsPresenter) {
        this.presenter = presenter
    }

    override fun setupFabVisibility(user: UserInfo?) {
        val fab = view!!.findViewById<FloatingActionButton>(R.id.add_element)

        if (user == null || !user.isAdmin) {
            fab.hide()
        } else {
            fab.setOnClickListener {
                presenter.onFabPressed()
            }
        }
    }

    override fun showSections(sectionNames: List<String>) {
        recyclerView.adapter = SectionAdapter(sectionNames)
    }

    override fun openSection(section: String) {
        val intent = Intent(context, ViewSectionActivity::class.java)
        intent.putExtra(ViewSectionActivity.EXTRA_SECTION_NAME, section)
        Log.d(Constants.SECTION_LOG_TAG, "Starting ViewSectionActivity with section $section")
        startActivity(intent)
    }

    override fun addSection() {
        dialog.show()
    }
    //endregion

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        recyclerView = view!!.findViewById(R.id.feed_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Добавление раздела")
        builder.setMessage("Введите название раздела")
        val input = EditText(activity)
        input.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        )
        builder.setView(input)
        builder.setPositiveButton("ОК") { _, _ ->
            presenter.addNewSection(input.text.toString())
        }
        builder.setNegativeButton("Отмена") { _, _ -> }
        dialog = builder.create()

        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }
    //endregion

    //region Holder
    private inner class SectionHolder(inflater: LayoutInflater, container: ViewGroup):
            RecyclerView.ViewHolder(inflater.inflate(R.layout.item_section, container, false)),
            View.OnClickListener {
        private lateinit var section: String
        private val titleTextView: TextView = itemView.findViewById(R.id.section_name)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(sectionName: String){
            this.section = sectionName
            titleTextView.text = sectionName
        }

        override fun onClick(view: View?) {
            presenter.onSectionSelected(section)
        }
    }
    //endregion

    //region Adapter
    private inner class SectionAdapter(private val sections: List<String>): RecyclerView.Adapter<SectionHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): SectionHolder {
            val inflater = LayoutInflater.from(activity)
            return SectionHolder(inflater, parent)
        }

        override fun getItemCount() = sections.size

        override fun onBindViewHolder(holder: SectionHolder, position: Int) {
            val section = sections[position]
            holder.bind(section)
        }
    }
    //endregion
}