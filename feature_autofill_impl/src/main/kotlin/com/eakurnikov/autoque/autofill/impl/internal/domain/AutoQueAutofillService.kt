package com.eakurnikov.autoque.autofill.impl.internal.domain

import android.annotation.TargetApi
import android.os.Build
import android.os.CancellationSignal
import android.service.autofill.AutofillService
import android.service.autofill.FillCallback
import android.service.autofill.FillRequest
import android.service.autofill.SaveCallback
import android.service.autofill.SaveRequest
import com.eakurnikov.autoque.autofill.impl.di.components.AutofillServiceComponent
import com.eakurnikov.autoque.autofill.impl.internal.presentation.AutofillRequestPresenter
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 *
 * The AutoQue implementation of [AutofillService].
 */
@TargetApi(Build.VERSION_CODES.O)
class AutoQueAutofillService : AutofillService() {

    @Inject
    lateinit var autofillRequestPresenter: AutofillRequestPresenter

    override fun onCreate() {
        super.onCreate()

        AutofillServiceComponent
            .initAndGet(Unit)
            .inject(this)
    }

    override fun onFillRequest(
        fillRequest: FillRequest,
        cancellationSignal: CancellationSignal,
        fillCallback: FillCallback
    ) {
        autofillRequestPresenter.onFillRequest(fillRequest, cancellationSignal, fillCallback)
    }

    override fun onSaveRequest(saveRequest: SaveRequest, saveCallback: SaveCallback) {
        autofillRequestPresenter.onSaveRequest(saveRequest, saveCallback)
    }
}