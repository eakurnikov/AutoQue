package com.eakurnikov.autoque.autofill.impl.api.presentation.disclaimer

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.service.autofill.Dataset
import android.view.autofill.AutofillManager
import com.eakurnikov.autoque.autofill.api.api.presentation.disclaimer.AutofillDisclaimerPresenter
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.AutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.ui.disclaimer.AutofillDisclaimerUi
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataId
import com.eakurnikov.autoque.autofill.impl.internal.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.fill.ProduceUnsafeDatasetUseCase
import com.eakurnikov.autoque.autofill.impl.internal.extensions.getFillDataId
import com.eakurnikov.autoque.autofill.impl.internal.extensions.getRequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.extensions.log
import com.eakurnikov.autoque.autofill.impl.internal.ui.AutofillUi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-16
 */
@Suppress("CheckResult")
@TargetApi(Build.VERSION_CODES.O)
class AutofillDisclaimerPresenterImpl @Inject constructor(
    private val produceUnsafeDatasetUseCase: ProduceUnsafeDatasetUseCase,
    private val autofillUi: AutofillUi
) : AutofillDisclaimerPresenter {

    private val tag: String = "AutofillDisclaimerPresenter"

    override fun onDisclaimerResponse(
        disclaimerUi: AutofillDisclaimerUi,
        disclaimerPayload: AutofillPayload?,
        allowAutofill: Boolean
    ) {
        if (!allowAutofill) {
            log("$tag: Fill denied by user via disclaimer")
            disclaimerUi.activityContext.setResult(Activity.RESULT_CANCELED)
            disclaimerUi.finish()
            autofillUi.showAsToast(R.string.faf_fill_canceled)
            return
        }

        if (disclaimerPayload == null) {
            log("$tag: Fill payload is null")
            disclaimerUi.finish()
            autofillUi.showAsToast(R.string.faf_fill_failure)
            return
        }

        val fillRequestInfo: RequestInfo? = disclaimerPayload.clientState.getRequestInfo()

        if (fillRequestInfo == null) {
            log("$tag: Fill request info is null")
            disclaimerUi.finish()
            autofillUi.showAsToast(R.string.faf_fill_failure)
            return
        }

        val fillDataId: FillDataId? = disclaimerPayload.clientState.getFillDataId()

        if (fillDataId == null) {
            log("$tag: Fill data id is null")
            disclaimerUi.finish()
            autofillUi.showAsToast(R.string.faf_fill_failure)
            return
        }

        produceUnsafeDatasetUseCase
            .invoke(fillDataId, fillRequestInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(OnUnsafeDatasetProducedObserver(disclaimerUi))
    }

    private inner class OnUnsafeDatasetProducedObserver(
        private val disclaimerUi: AutofillDisclaimerUi
    ) : DisposableSingleObserver<Dataset>() {

        override fun onSuccess(unsafeDataset: Dataset) {
            val disclaimerResultIntent: Intent = Intent().apply {
                putExtra(AutofillManager.EXTRA_AUTHENTICATION_RESULT, unsafeDataset)
            }
            disclaimerUi.activityContext.setResult(Activity.RESULT_OK, disclaimerResultIntent)
            disclaimerUi.finish()
            dispose()
        }

        override fun onError(error: Throwable) {
            log("$tag: Error while returning unsafe dataset after disclaimer: $error", error)
            disclaimerUi.activityContext.setResult(Activity.RESULT_CANCELED)
            disclaimerUi.finish()
            autofillUi.showAsToast(R.string.faf_fill_failure)
            dispose()
        }
    }
}