package com.eakurnikov.autoque.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.eakurnikov.autoque.R
import com.eakurnikov.autoque.autofill.api.api.AutofillFeatureApi
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

    //todo mode to view model
    private val promptAutofillServiceSelection: () -> Unit = {
        if (::autofillApi.isInitialized) {
            with(autofillApi) {
                if (!autofillServiceEnabler.isEnabled) {
                    autofillServiceEnabler.isEnabled = true
                }

                if (!autofillServiceSelector.isSelected) {
                    autofillServiceSelector.promptSelection(this@SplashActivity, 0)
                } else {
                    finish()
                }
            }
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

        Handler(mainLooper).postDelayed(
            promptAutofillServiceSelection,
            TimeUnit.SECONDS.toMillis(2)
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0) {
            val isSelected: Boolean = resultCode == Activity.RESULT_OK
            autofillApi.autofillServiceSelector.onSelection(isSelected)

            //todo
//            AutofillPopup.Companion.cancelIfNecessary()

            if (isSelected) {
                autofill_registration_status.text = getString(R.string.autofill_service_selected)
                Handler(mainLooper).postDelayed({ finish() }, TimeUnit.SECONDS.toMillis(2))
            } else {
                Toast.makeText(
                    this@SplashActivity,
                    R.string.autofill_service_selection_canceled,
                    Toast.LENGTH_LONG
                ).show()

                finish()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}