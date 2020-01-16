package com.eakurnikov.autoque.autofill.impl.internal.presentation

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.service.autofill.*
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.autoque.autofill.impl.internal.data.enums.FillResponseType
import com.eakurnikov.autoque.autofill.impl.internal.data.enums.IntentSenderType
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillResponseResource
import com.eakurnikov.autoque.autofill.impl.internal.data.model.IntentSenderResource
import com.eakurnikov.autoque.autofill.impl.internal.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.domain.request.RequestInfoBuilder
import com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.fill.ProduceFillResponseUseCase
import com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.save.SaveFillDataUseCase
import com.eakurnikov.autoque.autofill.impl.internal.extensions.*
import com.eakurnikov.autoque.autofill.impl.internal.ui.AutofillUi
import com.eakurnikov.common.annotations.AppContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-09
 */
@Suppress("CheckResult")
@TargetApi(Build.VERSION_CODES.O)
class AutofillRequestPresenter @Inject constructor(
    @AppContext private val context: Context,
    private val requestInfoBuilder: RequestInfoBuilder,
    private val produceFillResponseUseCase: ProduceFillResponseUseCase,
    private val saveFillDataUseCase: SaveFillDataUseCase,
    private val autofillUi: AutofillUi
) {
    private val tag: String = "AutofillRequestPresenter"
    private var disposable: Disposable? = null

    fun onFillRequest(
        fillRequest: FillRequest,
        cancellationSignal: CancellationSignal,
        fillCallback: FillCallback
    ) {
        cancellationSignal.setOnCancelListener {
            disposable?.dispose()
            disposable = null
            fillCallback.onFailure(null)
        }

        val isManualRequest: Boolean = (fillRequest.flags and FillRequest.FLAG_MANUAL_REQUEST) != 0
        val fillRequestInfo: RequestInfo? = requestInfoBuilder.build(fillRequest)

        if (fillRequestInfo == null) {
            log("$tag: Fill request info is null")
            if (!cancellationSignal.isCanceled) {
                fillCallback.onFailure(null)
            }
            return
        }

        val clientState: Bundle = fillRequest.clientState ?: Bundle()
        val mergedFillRequestInfo: RequestInfo? = fillRequestInfo + clientState.getRequestInfo()

        if (mergedFillRequestInfo == null) {
            log("$tag: Merged fill request info is null")
            if (!cancellationSignal.isCanceled) {
                fillCallback.onFailure(null)
            }
            return
        }

        clientState.putRequestInfo(mergedFillRequestInfo)

        if (cancellationSignal.isCanceled) return

        disposable = produceFillResponseUseCase
            .invoke(mergedFillRequestInfo, clientState)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                OnFillResponseProducedObserver(
                    cancellationSignal,
                    fillCallback,
                    isManualRequest
                )
            )
    }

    fun onSaveRequest(saveRequest: SaveRequest, saveCallback: SaveCallback) {
        val clientState: Bundle? = saveRequest.clientState
        val fillRequestInfo: RequestInfo? = clientState?.getRequestInfo()

        if (fillRequestInfo == null) {
            log("$tag: No previous fill request's client state got while save request")
            saveCallback.onFailure(context.getString(R.string.faf_save_failure))
            return
        }

        val saveRequestInfo: RequestInfo? = requestInfoBuilder.build(saveRequest, fillRequestInfo)

        if (saveRequestInfo == null || !saveRequestInfo.isValid()) {
            log("$tag: Save request info is not valid or null")
            saveCallback.onFailure(context.getString(R.string.faf_save_failure))
            return
        }

        clientState.putRequestInfo(saveRequestInfo)

        saveFillDataUseCase
            .invoke(saveRequestInfo, clientState)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(OnSaveFillDataObserver(saveCallback))
    }

    private inner class OnFillResponseProducedObserver(
        private val cancellationSignal: CancellationSignal,
        private val fillCallback: FillCallback,
        private val isManualRequest: Boolean
    ) : DisposableSingleObserver<FillResponseResource>() {

        override fun onSuccess(resource: FillResponseResource) {
            if (resource.type == FillResponseType.EMPTY && isManualRequest) {
                autofillUi.showAsAutoQueToast(R.string.faf_dataset_title_no_datasets)
            }
            if (!cancellationSignal.isCanceled) {
                fillCallback.onSuccess(resource.fillResponse)
            }
            dispose()
        }

        override fun onError(error: Throwable) {
            log("$tag: Error while producing fill response: $error", error)
            if (!cancellationSignal.isCanceled) {
                fillCallback.onFailure(context.getString(R.string.faf_fill_failure))
            }
            dispose()
        }
    }

    private inner class OnSaveFillDataObserver(
        private val saveCallback: SaveCallback
    ) : DisposableMaybeObserver<IntentSenderResource>() {

        override fun onSuccess(resource: IntentSenderResource) {
            when (resource.type) {
                IntentSenderType.AUTH -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        saveCallback.onSuccess(resource.intentSender)
                    } else {
                        resource.intentSender.sendIntent(context, 0, null, null, null)
                        saveCallback.onSuccess()
                    }
                }
                IntentSenderType.PROMPT -> {
                    resource.intentSender.sendIntent(context, 0, null, null, null)
                    saveCallback.onSuccess()
                }
            }
            dispose()
        }

        override fun onComplete() {
            autofillUi.showAsToast(R.string.faf_save_success)
            saveCallback.onSuccess()
            dispose()
        }

        override fun onError(error: Throwable) {
            log("$tag: Error while saving fill data: $error", error)
            saveCallback.onFailure(context.getString(R.string.faf_save_failure))
            dispose()
        }
    }
}