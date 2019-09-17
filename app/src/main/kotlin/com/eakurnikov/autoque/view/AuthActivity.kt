package com.eakurnikov.autoque.view

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.eakurnikov.autoque.R
import com.eakurnikov.autoque.autofill.api.api.AutofillFeatureApi
import com.eakurnikov.autoque.autofill.api.api.auth.AutofillAuthListener
import com.eakurnikov.autoque.autofill.api.dependencies.auth.AutofillAuthPayload
import com.eakurnikov.autoque.autofill.api.dependencies.auth.AutofillAuthenticator
import com.eakurnikov.autoque.autofill.api.dependencies.auth.getAutofillAuthPayload
import com.eakurnikov.autoque.domain.AutoQueAuthentication
import dagger.android.AndroidInjection
import dagger.android.DaggerActivity
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_auth.no_auth_btn
import kotlinx.android.synthetic.main.activity_auth.yes_auth_btn

/**
 * Created by eakurnikov on 2019-09-15
 */
class AuthActivity : DaggerActivity(), AutofillAuthenticator {

    @Inject
    lateinit var autofillApi: AutofillFeatureApi

    override val activityContext: Activity = this@AuthActivity

    override val autofillAuthListener: AutofillAuthListener
        get() = autofillApi.autofillAuthListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        AndroidInjection.inject(this)

        yes_auth_btn.setOnClickListener(::auth)
        no_auth_btn.setOnClickListener(::authFailed)
    }

    private fun auth(view: View) {
        AutoQueAuthentication.authenticate(this@AuthActivity, AutoQueAuthentication.duration)
        val authPayload: AutofillAuthPayload? = intent.getAutofillAuthPayload()

        autofillAuthListener.onAuth(this, authPayload, authPayload != null)
    }

    private fun authFailed(view: View) {
        autofillAuthListener.onAuth(this, null, false)
    }
}