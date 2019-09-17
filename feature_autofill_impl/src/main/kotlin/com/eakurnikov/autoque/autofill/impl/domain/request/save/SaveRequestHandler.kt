package com.eakurnikov.autoque.autofill.impl.domain.request.save

import android.content.Context
import android.content.IntentSender
import android.os.Build
import android.os.Bundle
import android.service.autofill.SaveCallback
import android.service.autofill.SaveRequest
import android.widget.Toast
import com.eakurnikov.autoque.autofill.api.dependencies.auth.AutofillAuthProvider
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.autoque.autofill.impl.data.Resource
import com.eakurnikov.autoque.autofill.impl.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.domain.request.RequestInfoBuilder
import com.eakurnikov.autoque.autofill.impl.extensions.getRequestInfo
import com.eakurnikov.autoque.autofill.impl.extensions.isValid
import com.eakurnikov.autoque.autofill.impl.extensions.putRequestInfo
import com.eakurnikov.autoque.autofill.impl.util.SaveResource
import com.eakurnikov.common.annotations.AppContext
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 *
 * Replies on an autofill service's [SaveRequest] with an authentication intent got from
 * [AutofillAuthenticationProvider] or saving filled user data via [FillDataSaver].
 */
class SaveRequestHandler @Inject constructor(
    @AppContext private val context: Context,
    private val autofillAuthProvider: AutofillAuthProvider<*>,
    private val requestInfoBuilder: RequestInfoBuilder,
    private val fillDataSaver: FillDataSaver
) {
    private var disposable: Disposable? = null

    fun handleSaveRequest(saveRequest: SaveRequest, saveCallback: SaveCallback) {
        val clientState: Bundle? = saveRequest.clientState
        val fillRequestInfo: RequestInfo? = clientState?.getRequestInfo()

        if (fillRequestInfo == null) {
            saveCallback.onFailure(context.getString(R.string.faf_save_data_failure))
            return
        }

        val saveRequestInfo: RequestInfo? = requestInfoBuilder.build(saveRequest, fillRequestInfo)

        if (saveRequestInfo == null || !saveRequestInfo.isValid()) {
            saveCallback.onFailure(context.getString(R.string.faf_save_data_failure))
            return
        }

        saveFillData(clientState.apply { putRequestInfo(saveRequestInfo) }, saveCallback)
    }

    private fun saveFillData(clientState: Bundle, saveCallback: SaveCallback) {
        if (autofillAuthProvider.isAuthRequired) {
            authenticateAndSaveFillData(clientState, saveCallback)
        } else {
            authenticatedSaveFillData(clientState, saveCallback)
        }
    }

    private fun authenticateAndSaveFillData(clientState: Bundle, saveCallback: SaveCallback) {
        val authIntentSender: IntentSender = autofillAuthProvider.getAuthIntentSenderForSave(clientState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            saveCallback.onSuccess(authIntentSender)
        } else {
            authIntentSender.sendIntent(context, 0, null, null, null)
        }
    }

    private fun authenticatedSaveFillData(clientState: Bundle, saveCallback: SaveCallback) {
        disposable = fillDataSaver
            .saveFillData(clientState)
            .subscribe(
                { saveResource: SaveResource ->
                    when (saveResource) {
                        is Resource.Success -> {
                            Toast.makeText(
                                context,
                                context.getString(R.string.faf_save_data_success),
                                Toast.LENGTH_LONG
                            ).show()

                            saveCallback.onSuccess()
                        }
                        is Resource.Loading -> Unit
                        is Resource.Error -> {
                            saveCallback.onFailure(
                                context.getString(R.string.faf_save_data_failure)
                            )
                        }
                    }
                    unsubscribe()
                },
                { error: Throwable ->
                    saveCallback.onFailure(
                        context.getString(R.string.faf_save_data_failure)
                    )
                    unsubscribe()
                }
            )
    }

    private fun unsubscribe() {
        disposable?.dispose()
        disposable = null
    }
}