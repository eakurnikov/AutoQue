package com.eakurnikov.autoque.autofill.impl.domain

import android.annotation.TargetApi
import android.os.Build
import android.os.CancellationSignal
import android.service.autofill.*
import com.eakurnikov.autoque.autofill.impl.di.components.AutofillServiceComponent
import com.eakurnikov.autoque.autofill.impl.domain.request.fill.FillRequestHandler
import com.eakurnikov.autoque.autofill.impl.domain.request.save.SaveRequestHandler
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 *
 * The AutoQue implementation of [AutofillService].
 */
@TargetApi(Build.VERSION_CODES.O)
class AutoQueAutofillService : AutofillService() {

    @Inject
    lateinit var fillRequestHandler: FillRequestHandler

    @Inject
    lateinit var saveRequestHandler: SaveRequestHandler

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
        fillRequestHandler.handleFillRequest(fillRequest, cancellationSignal, fillCallback)
    }

    override fun onSaveRequest(saveRequest: SaveRequest, saveCallback: SaveCallback) {
        saveRequestHandler.handleSaveRequest(saveRequest, saveCallback)
    }
}