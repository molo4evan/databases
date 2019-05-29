package ru.nsu.ccfit.android.fitwiki.view.navigation.fragments.backups

import android.app.AlertDialog
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import ru.nsu.ccfit.android.fitwiki.R
import ru.nsu.ccfit.android.fitwiki.model.Backup
import ru.nsu.ccfit.android.fitwiki.view.base.BaseFragment
import java.text.SimpleDateFormat
import java.util.*

class BackupsFragment: BaseFragment(R.layout.fragment_view_add_elements), IBackupsView {
        private lateinit var recyclerView: RecyclerView
    private lateinit var presenter: IBackupsPresenter

    private lateinit var dialog: AlertDialog

    //region IBackupsView
    override fun showBackups(backups: List<Backup>) {
        recyclerView.adapter = BackupAdapter(backups)
    }

    override fun addBackup() {
        dialog.show()
    }

    override fun setPresenter(presenter: IBackupsPresenter) {
        this.presenter = presenter
    }
    //endregion

    //region Lifecycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        recyclerView = view!!.findViewById(R.id.feed_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val fab = view.findViewById<FloatingActionButton>(R.id.add_element)
        fab.setOnClickListener {
            presenter.onFabPressed()
        }

        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Добавление резервной копии")
                .setMessage("Введите название резервной копии")
        val input = EditText(activity)
        input.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        )
        builder.setView(input)
                .setPositiveButton("ОК") { _, _ ->
            presenter.addNewBackup(input.text.toString())
        }
                .setNegativeButton("Отмена") { _, _ -> }
        dialog = builder.create()

        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }
    //endregion

    //region Holder
    private inner class BackupHolder(inflater: LayoutInflater, container: ViewGroup):
            RecyclerView.ViewHolder(inflater.inflate(R.layout.item_backup, container, false)),
            View.OnClickListener {
        private lateinit var backup: Backup
        private val titleTextView: TextView = itemView.findViewById(R.id.backup_name)
        private val dateTextView: TextView = itemView.findViewById(R.id.backup_date)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(backup: Backup){
            this.backup = backup
            titleTextView.text = backup.name
            dateTextView.text = SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH).format(backup.date)
        }

        override fun onClick(view: View?) {
            presenter.onBackupSelected(backup)
        }
    }
    //endregion

    //region Adapter
    private inner class BackupAdapter(private val backups: List<Backup>): RecyclerView.Adapter<BackupHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): BackupHolder {
            val inflater = LayoutInflater.from(activity)
            return BackupHolder(inflater, parent)
        }

        override fun getItemCount() = backups.size

        override fun onBindViewHolder(holder: BackupHolder, position: Int) {
            val backup = backups[position]
            holder.bind(backup)
        }
    }
    //endregion
}