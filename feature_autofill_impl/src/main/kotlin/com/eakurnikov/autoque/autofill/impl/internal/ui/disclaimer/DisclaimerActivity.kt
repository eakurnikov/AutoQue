package com.eakurnikov.autoque.autofill.impl.internal.ui.disclaimer

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.getAutofillPayload
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.autoque.autofill.impl.internal.viewmodel.disclaimer.DisclaimerViewModel
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.faf_activity_disclaimer.*
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-16
 */
class DisclaimerActivity : DaggerAppCompatActivity(), DisclaimerUi {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var viewModel: DisclaimerViewModel

    override val activityContext: Activity = this@DisclaimerActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.faf_activity_disclaimer)

        AndroidInjection.inject(this)

        viewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(DisclaimerViewModel::class.java)

        initViews()
    }

    private fun initViews() {
        faf_disclaimer_btn_yes.setOnClickListener(::onUnsafeAutofillAllowed)
        faf_disclaimer_btn_no.setOnClickListener(::onUnsafeAutofillRejected)
    }

    private fun onUnsafeAutofillAllowed(view: View) {
        viewModel.onDisclaimerResponse(this, intent.getAutofillPayload(), true)
    }

    private fun onUnsafeAutofillRejected(view: View) {
        viewModel.onDisclaimerResponse(this, intent.getAutofillPayload(), false)
    }
}