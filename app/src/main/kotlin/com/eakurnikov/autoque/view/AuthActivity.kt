package com.eakurnikov.autoque.view

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.eakurnikov.autoque.R
import com.eakurnikov.autoque.autofill.api.api.AutofillFeatureApi
import com.eakurnikov.autoque.autofill.api.api.presentation.auth.AutofillAuthPresenter
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.AutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.getAutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.ui.auth.AutofillAuthUi
import com.eakurnikov.autoque.domain.autofill.auth.AutoQueAuthentication
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_auth.no_auth_btn
import kotlinx.android.synthetic.main.activity_auth.yes_auth_btn

/**
 * Created by eakurnikov on 2019-09-15
 */
class AuthActivity : DaggerAppCompatActivity(), AutofillAuthUi {

    @Inject
    lateinit var autofillApi: AutofillFeatureApi

    override val activityContext: Activity = this@AuthActivity

    override val autofillAuthPresenter: AutofillAuthPresenter = autofillApi.autofillAuthPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        AndroidInjection.inject(this)

        yes_auth_btn.setOnClickListener(::onAuthSucceeded)
        no_auth_btn.setOnClickListener(::onAuthFailed)
    }

    private fun onAuthSucceeded(view: View) {
        AutoQueAuthentication.authenticate(this@AuthActivity, AutoQueAuthentication.duration)
        val authPayload: AutofillPayload? = intent.getAutofillPayload()
        autofillAuthPresenter.onAuthenticate(this, authPayload, authPayload != null)
    }

    private fun onAuthFailed(view: View) {
        autofillAuthPresenter.onAuthenticate(this, null, false)
    }
}