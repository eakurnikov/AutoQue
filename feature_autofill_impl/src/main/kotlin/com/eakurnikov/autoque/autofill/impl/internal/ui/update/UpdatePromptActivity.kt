package com.eakurnikov.autoque.autofill.impl.internal.ui.update

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.getAutofillPayload
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.autoque.autofill.impl.internal.viewmodel.update.UpdatePromptViewModel
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.faf_activity_update_prompt.*
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-09
 */
class UpdatePromptActivity : DaggerAppCompatActivity(), UpdatePromptUi {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var viewModel: UpdatePromptViewModel

    override val activityContext: Activity = this@UpdatePromptActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.faf_activity_update_prompt)

        AndroidInjection.inject(this)

        viewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(UpdatePromptViewModel::class.java)

        initViews()
    }

    private fun initViews() {
        faf_update_prompt_btn_yes.setOnClickListener(::onUpdateAllowed)
        faf_update_prompt_btn_no.setOnClickListener(::onUpdateRejected)
    }

    private fun onUpdateAllowed(view: View) {
        viewModel.onPromptResponse(this, intent.getAutofillPayload(), true)
    }

    private fun onUpdateRejected(view: View) {
        viewModel.onPromptResponse(this, intent.getAutofillPayload(), false)
    }
}