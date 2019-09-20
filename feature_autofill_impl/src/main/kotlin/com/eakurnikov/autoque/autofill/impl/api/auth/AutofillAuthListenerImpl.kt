package com.eakurnikov.autoque.autofill.impl.api.auth

import android.app.Activity.RESULT_CANCELED
import android.content.Intent
import android.os.Bundle
import android.service.autofill.FillResponse
import android.view.autofill.AutofillManager
import android.widget.Toast
import com.eakurnikov.autoque.autofill.api.api.auth.AutofillAuthListener
import com.eakurnikov.autoque.autofill.api.dependencies.auth.AutofillAuthPayload
import com.eakurnikov.autoque.autofill.api.dependencies.auth.AutofillAuthType
import com.eakurnikov.autoque.autofill.api.dependencies.auth.AutofillAuthenticator
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.autoque.autofill.impl.data.Resource
import com.eakurnikov.autoque.autofill.impl.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.domain.request.fill.FillResponseProducer
import com.eakurnikov.autoque.autofill.impl.domain.request.save.FillDataSaver
import com.eakurnikov.autoque.autofill.impl.extensions.getRequestInfo
import com.eakurnikov.autoque.autofill.impl.util.FillResponseResource
import com.eakurnikov.autoque.autofill.impl.util.SaveResource
import dagger.android.DaggerActivity
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 */
class AutofillAuthListenerImpl @Inject constructor(
    private val fillResponseProducer: FillResponseProducer,
    private val fillDataSaver: FillDataSaver
) : AutofillAuthListener {

    private var fillDisposable: Disposable? = null
    private var saveDisposable: Disposable? = null

    override fun onAuth(authenticator: AutofillAuthenticator, authPayload: AutofillAuthPayload?, authResult: Boolean) {
        if (!authResult || authPayload == null) {
            onAuthFailure(authenticator, authPayload?.authType)
            return
        }

        val clientState: Bundle = authPayload.clientState

        when (authPayload.authType) {
            AutofillAuthType.FILL -> onAuthForFill(authenticator, clientState)
            AutofillAuthType.SAVE -> onAuthForSave(authenticator, clientState)
        }
    }

    private fun onAuthForFill(authenticator: AutofillAuthenticator, clientState: Bundle) {
        val requestInfo: RequestInfo? = clientState.getRequestInfo()

        if (requestInfo == null) {
            onAuthFailure(authenticator, AutofillAuthType.FILL)
            return
        }

        fillDisposable = fillResponseProducer
            .produceUnlockedFillResponse(requestInfo, clientState)
            .subscribe(
                { fillResponseResource: FillResponseResource ->
                    when (fillResponseResource) {
                        is Resource.Success -> {
                            onAuthSuccess(authenticator, AutofillAuthType.FILL, fillResponseResource.data)
                        }
                        is Resource.Loading -> Unit
                        is Resource.Error -> {
                            onAuthFailure(authenticator, AutofillAuthType.FILL)
                        }
                    }
                },
                { error: Throwable ->
                    onAuthFailure(authenticator, AutofillAuthType.FILL)
                }
            )
    }

    private fun onAuthForSave(authenticator: AutofillAuthenticator, clientState: Bundle) {
        val requestInfo: RequestInfo? = clientState.getRequestInfo()

        if (requestInfo == null) {
            onAuthFailure(authenticator, AutofillAuthType.SAVE)
            return
        }

        saveDisposable = fillDataSaver
            .saveFillData(requestInfo)
            .subscribe(
                { saveResource: SaveResource ->
                    when (saveResource) {
                        is Resource.Success -> {
                            onAuthSuccess(authenticator, AutofillAuthType.SAVE)
                        }
                        is Resource.Loading -> Unit
                        is Resource.Error -> {
                            onAuthFailure(authenticator, AutofillAuthType.SAVE)
                        }
                    }
                },
                { error: Throwable ->
                    onAuthFailure(authenticator, AutofillAuthType.SAVE)
                }
            )
    }

    private fun onAuthSuccess(
        authenticator: AutofillAuthenticator,
        authType: AutofillAuthType,
        fillResponse: FillResponse? = null
    ) {
        when (authType) {
            AutofillAuthType.FILL -> {
                fillResponse?.let {
                    authenticator.activityContext.setResult(
                        DaggerActivity.RESULT_OK,
                        Intent().apply {
                            putExtra(AutofillManager.EXTRA_AUTHENTICATION_RESULT, it)
                        }
                    )
                } ?: onAuthFailure(authenticator, authType)
            }
            AutofillAuthType.SAVE -> {
                Toast.makeText(
                    authenticator.activityContext,
                    authenticator.activityContext.getString(R.string.faf_save_data_success),
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> Unit
        }

        finish(authenticator)
    }

    private fun onAuthFailure(authenticator: AutofillAuthenticator, authType: AutofillAuthType?) {
        when (authType) {
            AutofillAuthType.FILL -> {
                Toast.makeText(
                    authenticator.activityContext,
                    authenticator.activityContext.getString(R.string.faf_fill_authentication_failure),
                    Toast.LENGTH_LONG
                ).show()

                authenticator.activityContext.setResult(RESULT_CANCELED)
            }
            AutofillAuthType.SAVE -> {
                Toast.makeText(
                    authenticator.activityContext,
                    authenticator.activityContext.getString(R.string.faf_save_authentication_failure),
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
                Toast.makeText(
                    authenticator.activityContext,
                    authenticator.activityContext.getString(R.string.faf_authentication_failure),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        finish(authenticator)
    }

    private fun unsubscribe() {
        fillDisposable?.dispose()
        fillDisposable = null

        saveDisposable?.dispose()
        saveDisposable = null
    }

    private fun finish(authenticator: AutofillAuthenticator) {
        unsubscribe()
        authenticator.activityContext.finish()
    }
}