package com.eakurnikov.autoque.view.autofill

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.eakurnikov.autoque.R
import com.eakurnikov.autoque.autofill.api.api.AutofillFeatureApi
import com.eakurnikov.autoque.autofill.api.api.presentation.update.AutofillUpdatePromptPresenter
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.getAutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.ui.update.AutofillUpdatePromptUi
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-09
 */
class AutofillUpdatePromptActivity : DaggerAppCompatActivity(), AutofillUpdatePromptUi {

    @Inject
    lateinit var autofillApi: AutofillFeatureApi

    override val activityContext: Activity = this@AutofillUpdatePromptActivity

    override val autofillUpdatePromptPresenter: AutofillUpdatePromptPresenter
        get() = autofillApi.autofillUpdatePromptPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.faf_activity_update_prompt)

        AndroidInjection.inject(this)

        findViewById<Button>(R.id.faf_update_prompt_btn_yes).setOnClickListener(::onUpdateAllowed)
        findViewById<Button>(R.id.faf_update_prompt_btn_no).setOnClickListener(::onUpdateRejected)
    }

    private fun onUpdateAllowed(view: View) {
        autofillUpdatePromptPresenter.onPromptResponse(
            this,
            intent.getAutofillPayload(),
            true
        )
    }

    private fun onUpdateRejected(view: View) {
        autofillUpdatePromptPresenter.onPromptResponse(
            this,
            intent.getAutofillPayload(),
            false
        )
    }
}