package ru.nsu.ccfit.android.fitwiki.view.signup

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up.*
import ru.nsu.ccfit.android.fitwiki.R
import ru.nsu.ccfit.android.fitwiki.control.ControllerHolder
import ru.nsu.ccfit.android.fitwiki.view.base.BaseActivity

class SignUpActivity : BaseActivity(), ISignUpView {
    private lateinit var presenter: ISignUpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val presenter = SignUpPresenter(this)
        presenter.setControllers(ControllerHolder.controllers)
        setPresenter(presenter)
        username_sign_up_button.setOnClickListener {
            val url = photoUrl.text.toString()
            presenter.onRegister(
                    username.text.toString(),
                    password.text.toString(),
                    if (url.isBlank()) null else url
            )
        }
    }

    override fun returnStatus(success: Boolean) {
        setResult(if (success) Activity.RESULT_OK else Activity.RESULT_CANCELED)
        finish()
    }

    override fun setPresenter(presenter: ISignUpPresenter) {
        this.presenter = presenter
    }
}
