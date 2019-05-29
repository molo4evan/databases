package ru.nsu.ccfit.android.fitwiki.view.createarticle

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_create_article.*
import ru.nsu.ccfit.android.fitwiki.R
import ru.nsu.ccfit.android.fitwiki.control.ControllerHolder
import ru.nsu.ccfit.android.fitwiki.view.base.BaseActivity

class CreateArticleActivity: BaseActivity(), ICreateArticleView {
    private lateinit var presenter: ICreateArticlePresenter
    private lateinit var spinner: Spinner
    private val sectionNames = mutableListOf<String>()
    private var sectionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_article)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        title = getString(R.string.create_article_title)

        val presenter = CreateArticlePresenter(this)
        presenter.setControllers(ControllerHolder.controllers)
        setPresenter(presenter)

        spinner = findViewById(R.id.section)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                sectionIndex = position
            }

        }

        submit.setOnClickListener {
            val title = findViewById<EditText>(R.id.name)
            val summary =  findViewById<EditText>(R.id.summary)
            val text = findViewById<EditText>(R.id.text)
            val sectionName = sectionNames[sectionIndex]
            val summaryText = summary.text.toString()
            presenter.createArticle(
                    title.text.toString(),
                    text.text.toString(),
                    sectionName,
                    if (summaryText.isEmpty()) null else summaryText
            )
        }
        cancel.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun dispose() {
        finish()
    }

    override fun setUpSections(sections: List<String>) {
        sectionNames.clear()
        sectionNames.addAll(sections)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sectionNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    override fun setPresenter(presenter: ICreateArticlePresenter) {
        this.presenter = presenter
    }

    private fun showForbidden(){

    }
}