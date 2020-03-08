package com.eakurnikov.autoque.autofill.impl.internal.viewmodel.disclaimer

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.autofill.AutofillManager
import androidx.lifecycle.ViewModel
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.AutofillPayload
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.autoque.autofill.impl.internal.data.enums.UnsafeDatasetType
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataId
import com.eakurnikov.autoque.autofill.impl.internal.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.data.model.UnsafeDatasetResource
import com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.fill.ProduceUnsafeDatasetUseCase
import com.eakurnikov.autoque.autofill.impl.internal.extensions.getFillDataId
import com.eakurnikov.autoque.autofill.impl.internal.extensions.getRequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.extensions.log
import com.eakurnikov.autoque.autofill.impl.internal.ui.autofill.AutofillUi
import com.eakurnikov.autoque.autofill.impl.internal.ui.disclaimer.DisclaimerUi
import com.eakurnikov.common.annotations.AppContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-14
 */
@TargetApi(Build.VERSION_CODES.O)
class DisclaimerViewModel @Inject constructor(
    @AppContext private val context: Context,
    private val produceUnsafeDatasetUseCase: ProduceUnsafeDatasetUseCase,
    private val autofillUi: AutofillUi
) : ViewModel() {

    private val tag: String = "DisclaimerViewModel"

    fun onDisclaimerResponse(
        disclaimerUi: DisclaimerUi,
        disclaimerPayload: AutofillPayload?,
        allowAutofill: Boolean
    ) {
        if (!allowAutofill) {
            log("$tag: Unsafe fill denied by user via disclaimer")
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
            .invoke(fillDataId, fillRequestInfo, disclaimerPayload.clientState)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(OnUnsafeDatasetProducedObserver(disclaimerUi))
    }

    private inner class OnUnsafeDatasetProducedObserver(
        private val disclaimerUi: DisclaimerUi
    ) : DisposableSingleObserver<UnsafeDatasetResource>() {

        override fun onSuccess(resource: UnsafeDatasetResource) {
            when (resource.type) {
                UnsafeDatasetType.LOCKED -> {
                    resource.intentSender
                        ?.sendIntent(context, 0, null, null, null)
                        ?: log("$tag: Auth intent for unsafe dataset is null somehow")
                }
                UnsafeDatasetType.UNLOCKED -> {
                    if (resource.dataset == null) {
                        log("$tag: Unsafe dataset is null somehow")
                    } else {
                        val resultIntent: Intent = Intent().apply {
                            putExtra(AutofillManager.EXTRA_AUTHENTICATION_RESULT, resource.dataset)
                        }
                        disclaimerUi.activityContext.setResult(Activity.RESULT_OK, resultIntent)
                    }
                }
            }
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