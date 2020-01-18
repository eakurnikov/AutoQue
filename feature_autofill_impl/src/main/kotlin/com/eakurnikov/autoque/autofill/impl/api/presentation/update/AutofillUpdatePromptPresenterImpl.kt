package com.eakurnikov.autoque.autofill.impl.api.presentation.update

import android.content.Context
import com.eakurnikov.autoque.autofill.api.api.presentation.update.AutofillUpdatePromptPresenter
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.AutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.ui.update.AutofillUpdatePromptUi
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.autoque.autofill.impl.internal.data.enums.IntentSenderType
import com.eakurnikov.autoque.autofill.impl.internal.data.model.IntentSenderResource
import com.eakurnikov.autoque.autofill.impl.internal.extensions.getRequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.extensions.log
import com.eakurnikov.autoque.autofill.impl.internal.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.save.UpdateFillDataUseCase
import com.eakurnikov.autoque.autofill.impl.internal.ui.AutofillUi
import com.eakurnikov.common.annotations.AppContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-16
 */
@Suppress("CheckResult")
class AutofillUpdatePromptPresenterImpl @Inject constructor(
    @AppContext private val context: Context,
    private val updateFillDataUseCase: UpdateFillDataUseCase,
    private val autofillUi: AutofillUi
) : AutofillUpdatePromptPresenter {

    private val tag: String = "AutofillUpdatePromptPresenter"

    override fun onPromptResponse(
        promptUi: AutofillUpdatePromptUi,
        promptPayload: AutofillPayload?,
        shouldUpdate: Boolean
    ) {
        if (!shouldUpdate) {
            promptUi.finish()
            return
        }

        if (promptPayload == null) {
            log("$tag: Save payload is null")
            promptUi.finish()
            autofillUi.showAsToast(R.string.faf_save_failure)
            return
        }

        val saveRequestInfo: RequestInfo? = promptPayload.clientState.getRequestInfo()

        if (saveRequestInfo == null) {
            log("$tag: Save request info is null")
            promptUi.finish()
            autofillUi.showAsToast(R.string.faf_save_failure)
            return
        }

        updateFillDataUseCase
            .invoke(saveRequestInfo, promptPayload.clientState)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(OnUpdateFillDataObserver(promptUi))
    }

    private inner class OnUpdateFillDataObserver(
        private val promptUi: AutofillUpdatePromptUi
    ) : DisposableMaybeObserver<IntentSenderResource>() {

        override fun onSuccess(resource: IntentSenderResource) {
            when (resource.type) {
                IntentSenderType.AUTH -> {
                    resource.intentSender.sendIntent(context, 0, null, null, null)
                }
                IntentSenderType.PROMPT -> {
                    log("$tag: Cycled update prompt require")
                }
            }
            promptUi.finish()
            dispose()
        }

        override fun onComplete() {
            promptUi.finish()
            autofillUi.showAsAutoQueToast(R.string.faf_save_success)
            dispose()
        }

        override fun onError(error: Throwable) {
            log("$tag: Error while saving fill data: $error", error)
            promptUi.finish()
            autofillUi.showAsToast(promptUi.activityContext.getString(R.string.faf_save_failure))
            dispose()
        }
    }
}