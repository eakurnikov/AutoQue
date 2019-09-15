package com.eakurnikov.autoque.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.eakurnikov.autoque.autofill.api.api.AutofillFeatureApi
import com.eakurnikov.autoque.autofill.api.api.selector.AutofillServiceSelector
import com.eakurnikov.autoque.R
import dagger.android.AndroidInjection
import dagger.android.DaggerActivity
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 */
class SplashActivity : DaggerActivity() {

    @Inject
    lateinit var autofillApi: AutofillFeatureApi

    private val promptAutofillServiceSelection: () -> Unit = {
        if (::autofillApi.isInitialized) {
            with(autofillApi) {
                if (!autofillServiceRegistrar.isRegistered) {
                    autofillServiceRegistrar.isRegistered = true
                }

                if (!autofillServiceSelector.isSelected) {
                    autofillServiceSelector.promptSelection(this@SplashActivity)
                } else {
                    finish()
                }
            }
        }
    }

    private val onAutofillServiceSelectionAction =
        object : AutofillServiceSelector.OnSelectionResultAction {
            override fun onSelected() {
                autofill_registration_status.text = getString(R.string.autofill_service_selected)
                Handler(mainLooper).postDelayed({ finish() }, TimeUnit.SECONDS.toMillis(2))
            }

            override fun onNotSelected() {
                Toast.makeText(
                    this@SplashActivity,
                    R.string.autofill_service_selection_canceled,
                    Toast.LENGTH_LONG
                ).show()

                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        AndroidInjection.inject(this)

        autofill_inject_status.text = getString(R.string.autofill_service_injected)

        autofill_registration_status.text = getString(
            if (autofillApi.autofillServiceSelector.isSelected)
                R.string.autofill_service_selected
            else
                R.string.autofill_service_not_selected
        )

        Handler(mainLooper).postDelayed(promptAutofillServiceSelection, TimeUnit.SECONDS.toMillis(2))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        autofillApi.autofillServiceSelector.onSelection(
            this@SplashActivity,
            requestCode,
            resultCode,
            data,
            onAutofillServiceSelectionAction
        )

        super.onActivityResult(requestCode, resultCode, data)
    }
}