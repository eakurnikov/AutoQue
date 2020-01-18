package com.eakurnikov.autoque.autofill.impl.api.presentation.auth

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.autofill.AutofillManager
import com.eakurnikov.autoque.autofill.api.api.presentation.auth.AutofillAuthPresenter
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.AutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.ui.auth.AutofillAuthUi
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.autoque.autofill.impl.internal.extensions.getRequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.extensions.log
import com.eakurnikov.autoque.autofill.impl.internal.data.enums.FillResponseType
import com.eakurnikov.autoque.autofill.impl.internal.data.enums.IntentSenderType
import com.eakurnikov.autoque.autofill.impl.internal.data.enums.UnsafeDatasetType
import com.eakurnikov.autoque.autofill.impl.internal.data.model.*
import com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.fill.ProduceFillResponseUseCase
import com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.fill.ProduceUnsafeDatasetUseCase
import com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.save.SaveFillDataUseCase
import com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.save.UpdateFillDataUseCase
import com.eakurnikov.autoque.autofill.impl.internal.extensions.getFillDataId
import com.eakurnikov.autoque.autofill.impl.internal.ui.AutofillUi
import com.eakurnikov.common.annotations.AppContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-16
 */
@Suppress("CheckResult")
@TargetApi(Build.VERSION_CODES.O)
class AutofillAuthPresenterImpl @Inject constructor(
    @AppContext private val context: Context,
    private val produceFillResponseUseCase: ProduceFillResponseUseCase,
    private val produceUnsafeDatasetUseCase: ProduceUnsafeDatasetUseCase,
    private val saveFillDataUseCase: SaveFillDataUseCase,
    private val updateFillDataUseCase: UpdateFillDataUseCase,
    private val autofillUi: AutofillUi
) : AutofillAuthPresenter {

    private val tag: String = "AutofillAuthPresenter"

    override fun onAuthenticate(
        authUi: AutofillAuthUi,
        authPayload: AutofillPayload?,
        authResult: Boolean
    ) {
        if (authPayload == null) {
            log("$tag: Auth failure: Payload is null")
            authUi.finish()
            autofillUi.showAsToast(R.string.faf_auth_failure)
            return
        }

        when (authPayload.type) {
            AutofillPayload.Type.FILL -> {
                onFillAuthenticate(authUi, authPayload, authResult)
            }
            AutofillPayload.Type.UNSAFE_FILL -> {
                onUnsafeFillAuthenticate(authUi, authPayload, authResult)
            }
            AutofillPayload.Type.SAVE -> {
                onSaveAuthenticate(authUi, authPayload, authResult)
            }
            AutofillPayload.Type.UPDATE -> {
                onUpdateAuthenticate(authUi, authPayload, authResult)
            }
        }
    }

    private fun onFillAuthenticate(
        authUi: AutofillAuthUi,
        authPayload: AutofillPayload,
        authResult: Boolean
    ) {
        if (!authResult) {
            log("$tag: Fill auth denied by user")
            authUi.activityContext.setResult(Activity.RESULT_CANCELED)
            authUi.finish()
            autofillUi.showAsToast(R.string.faf_fill_failure_auth)
            return
        }

        val fillRequestInfo: RequestInfo? = authPayload.clientState.getRequestInfo()

        if (fillRequestInfo == null) {
            log("$tag: Fill request info is null")
            authUi.activityContext.setResult(Activity.RESULT_CANCELED)
            authUi.finish()
            autofillUi.showAsToast(R.string.faf_fill_failure)
            return
        }

        produceFillResponseUseCase
            .invoke(fillRequestInfo, authPayload.clientState)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(OnFillResponseProducedObserver(authUi))
    }

    private fun onUnsafeFillAuthenticate(
        authUi: AutofillAuthUi,
        authPayload: AutofillPayload,
        authResult: Boolean
    ) {
        if (!authResult) {
            log("$tag: Unsafe fill auth denied by user")
            authUi.activityContext.setResult(Activity.RESULT_CANCELED)
            authUi.finish()
            autofillUi.showAsToast(R.string.faf_fill_failure_auth)
            return
        }

        val fillRequestInfo: RequestInfo? = authPayload.clientState.getRequestInfo()

        if (fillRequestInfo == null) {
            log("$tag: Fill request info is null")
            authUi.activityContext.setResult(Activity.RESULT_CANCELED)
            authUi.finish()
            autofillUi.showAsToast(R.string.faf_fill_failure)
            return
        }

        val fillDataId: FillDataId? = authPayload.clientState.getFillDataId()

        if (fillDataId == null) {
            log("$tag: Fill data entity id is null")
            authUi.finish()
            autofillUi.showAsToast(R.string.faf_fill_failure)
            return
        }

        produceUnsafeDatasetUseCase
            .invoke(fillDataId, fillRequestInfo, authPayload.clientState)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(OnUnsafeDatasetProducedObserver(authUi))
    }

    private fun onSaveAuthenticate(
        authUi: AutofillAuthUi,
        authPayload: AutofillPayload,
        authResult: Boolean
    ) {
        if (!authResult) {
            log("$tag: Save auth denied by user")
            authUi.finish()
            autofillUi.showAsToast(R.string.faf_save_failure_auth)
            return
        }

        val saveRequestInfo: RequestInfo? = authPayload.clientState.getRequestInfo()

        if (saveRequestInfo == null) {
            log("$tag: Save request info is null")
            authUi.finish()
            autofillUi.showAsToast(R.string.faf_save_failure)
            return
        }

        saveFillDataUseCase
            .invoke(saveRequestInfo, authPayload.clientState)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(OnSaveFillDataObserver(authUi))
    }

    private fun onUpdateAuthenticate(
        authUi: AutofillAuthUi,
        authPayload: AutofillPayload,
        authResult: Boolean
    ) {
        if (!authResult) {
            log("$tag: Update auth denied by user")
            authUi.finish()
            autofillUi.showAsToast(R.string.faf_save_failure_auth)
            return
        }

        val saveRequestInfo: RequestInfo? = authPayload.clientState.getRequestInfo()

        if (saveRequestInfo == null) {
            log("$tag: Save request info is null")
            authUi.finish()
            autofillUi.showAsToast(R.string.faf_save_failure)
            return
        }

        updateFillDataUseCase
            .invoke(saveRequestInfo, authPayload.clientState)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(OnUpdateFillDataObserver(authUi))
    }

    private inner class OnFillResponseProducedObserver(
        private val authUi: AutofillAuthUi
    ) : DisposableSingleObserver<FillResponseResource>() {

        override fun onSuccess(resource: FillResponseResource) {
            when (resource.type) {
                FillResponseType.LOCKED -> {
                    log("$tag: Fill auth is still required")
                }
                FillResponseType.UNLOCKED -> {
                    val resultIntent: Intent = Intent().apply {
                        putExtra(AutofillManager.EXTRA_AUTHENTICATION_RESULT, resource.fillResponse)
                    }
                    authUi.activityContext.setResult(Activity.RESULT_OK, resultIntent)
                }
                FillResponseType.EMPTY -> {
                    val resultIntent: Intent = Intent().apply {
                        putExtra(AutofillManager.EXTRA_AUTHENTICATION_RESULT, resource.fillResponse)
                    }
                    authUi.activityContext.setResult(Activity.RESULT_OK, resultIntent)
                    autofillUi.showAsAutoQueToast(R.string.faf_dataset_title_no_datasets)
                }
            }
            authUi.finish()
            dispose()
        }

        override fun onError(error: Throwable) {
            log("$tag: Error while completing fill reply after auth: $error", error)
            authUi.activityContext.setResult(Activity.RESULT_CANCELED)
            authUi.finish()
            autofillUi.showAsToast(R.string.faf_fill_failure)
            dispose()
        }
    }

    private inner class OnUnsafeDatasetProducedObserver(
        private val authUi: AutofillAuthUi
    ) : DisposableSingleObserver<UnsafeDatasetResource>() {

        override fun onSuccess(resource: UnsafeDatasetResource) {
            when (resource.type) {
                UnsafeDatasetType.LOCKED -> {
                    log("$tag: Unsafe fill auth is still required")
                }
                UnsafeDatasetType.UNLOCKED -> {
                    if (resource.dataset == null) {
                        log("$tag: Unsafe dataset is null somehow")
                    } else {
                        val resultIntent: Intent = Intent().apply {
                            putExtra(AutofillManager.EXTRA_AUTHENTICATION_RESULT, resource.dataset)
                        }
                        authUi.activityContext.setResult(Activity.RESULT_OK, resultIntent)
                    }
                }
            }
            authUi.finish()
            dispose()
        }

        override fun onError(error: Throwable) {
            log("$tag: Error while completing unsafe fill reply after auth: $error", error)
            authUi.activityContext.setResult(Activity.RESULT_CANCELED)
            authUi.finish()
            autofillUi.showAsToast(R.string.faf_fill_failure)
            dispose()
        }
    }

    private inner class OnSaveFillDataObserver(
        private val authUi: AutofillAuthUi
    ) : DisposableMaybeObserver<IntentSenderResource>() {

        override fun onSuccess(resource: IntentSenderResource) {
            when (resource.type) {
                IntentSenderType.AUTH -> {
                    log("$tag: Save auth is still required")
                }
                IntentSenderType.PROMPT -> {
                    resource.intentSender.sendIntent(context, 0, null, null, null)
                }
            }
            authUi.finish()
            dispose()
        }

        override fun onComplete() {
            authUi.finish()
            autofillUi.showAsAutoQueToast(R.string.faf_save_success)
            dispose()
        }

        override fun onError(error: Throwable) {
            log("$tag: Error while completing save reply after auth: $error", error)
            authUi.finish()
            autofillUi.showAsToast(authUi.activityContext.getString(R.string.faf_save_failure))
            dispose()
        }
    }

    private inner class OnUpdateFillDataObserver(
        private val authUi: AutofillAuthUi
    ) : DisposableMaybeObserver<IntentSenderResource>() {

        override fun onSuccess(resource: IntentSenderResource) {
            when (resource.type) {
                IntentSenderType.AUTH -> {
                    log("$tag: Update auth is still required")
                }
                IntentSenderType.PROMPT -> {
                    log("$tag: Cycled update prompt require")
                }
            }
            authUi.finish()
            dispose()
        }

        override fun onComplete() {
            authUi.finish()
            autofillUi.showAsAutoQueToast(R.string.faf_save_success)
            dispose()
        }

        override fun onError(error: Throwable) {
            log("$tag: Error while completing update reply after auth: $error", error)
            authUi.finish()
            autofillUi.showAsToast(authUi.activityContext.getString(R.string.faf_save_failure))
            dispose()
        }
    }
}