package ru.nsu.ccfit.android.fitwiki.view.signin

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_in.*
import ru.nsu.ccfit.android.fitwiki.R
import ru.nsu.ccfit.android.fitwiki.control.ControllerHolder
import ru.nsu.ccfit.android.fitwiki.view.base.BaseActivity

class SignInActivity : BaseActivity(), ISignInView {
    private lateinit var presenter: ISignInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val presenter = SignInPresenter(this)
        presenter.setControllers(ControllerHolder.controllers)
        setPresenter(presenter)
        username_sign_up_button.setOnClickListener {
            presenter.onLogin(username.text.toString(), password.text.toString())
        }
    }

    override fun returnStatus(success: Boolean) {
        setResult(if (success) Activity.RESULT_OK else Activity.RESULT_CANCELED)
        finish()
    }

    override fun setPresenter(presenter: ISignInPresenter) {
        this.presenter = presenter
    }
}
