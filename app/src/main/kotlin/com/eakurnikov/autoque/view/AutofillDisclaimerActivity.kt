package com.eakurnikov.autoque.view

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.eakurnikov.autoque.R
import com.eakurnikov.autoque.autofill.api.api.AutofillFeatureApi
import com.eakurnikov.autoque.autofill.api.api.presentation.disclaimer.AutofillDisclaimerPresenter
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.getAutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.ui.disclaimer.AutofillDisclaimerUi
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-16
 */
class AutofillDisclaimerActivity : DaggerAppCompatActivity(), AutofillDisclaimerUi {

    @Inject
    lateinit var autofillApi: AutofillFeatureApi

    override val activityContext: Activity = this@AutofillDisclaimerActivity

    override val autofillDisclaimerPresenter: AutofillDisclaimerPresenter
        get() = autofillApi.autofillDisclaimerPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.faf_activity_disclaimer)

        AndroidInjection.inject(this)

        findViewById<Button>(R.id.faf_disclaimer_btn_yes)
            .setOnClickListener(::onUnsafeAutofillAllowed)

        findViewById<Button>(R.id.faf_disclaimer_btn_no)
            .setOnClickListener(::onUnsafeAutofillRejected)
    }

    private fun onUnsafeAutofillAllowed(view: View) {
        autofillDisclaimerPresenter.onDisclaimerResponse(
            this,
            intent.getAutofillPayload(),
            true
        )
    }

    private fun onUnsafeAutofillRejected(view: View) {
        autofillDisclaimerPresenter.onDisclaimerResponse(
            this,
            intent.getAutofillPayload(),
            false
        )
    }
}