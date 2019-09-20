package com.eakurnikov.autoque.autofill.impl.domain.request.fill

import android.content.Context
import android.content.IntentSender
import android.os.Bundle
import android.os.CancellationSignal
import android.service.autofill.FillCallback
import android.service.autofill.FillRequest
import android.service.autofill.FillResponse
import android.widget.Toast
import com.eakurnikov.autoque.autofill.api.dependencies.auth.AutofillAuthProvider
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.autoque.autofill.impl.data.Resource
import com.eakurnikov.autoque.autofill.impl.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.domain.request.RequestInfoBuilder
import com.eakurnikov.autoque.autofill.impl.extensions.getRequestInfo
import com.eakurnikov.autoque.autofill.impl.extensions.plus
import com.eakurnikov.autoque.autofill.impl.extensions.putRequestInfo
import com.eakurnikov.autoque.autofill.impl.util.FillResponseResource
import com.eakurnikov.common.annotations.AppContext
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 *
 * Replies on an autofill service's [FillRequest] with locked [FillResponse] with authentication
 * intent got from [AutofillAuthenticationProvider] or unlocked [FillResponse] produced by
 * [FillResponseProducer].
 */
class FillRequestHandler @Inject constructor(
    @AppContext private val context: Context,
    private val autofillAuthProvider: AutofillAuthProvider<*>,
    private val requestInfoBuilder: RequestInfoBuilder,
    private val fillResponseProducer: FillResponseProducer
) {
    private var disposable: Disposable? = null

    fun handleFillRequest(
        fillRequest: FillRequest,
        cancellationSignal: CancellationSignal,
        fillCallback: FillCallback
    ) {
        cancellationSignal.setOnCancelListener {
            Toast.makeText(
                context,
                context.getString(R.string.faf_fill_canceled),
                Toast.LENGTH_LONG
            ).show()
        }

        var requestInfo: RequestInfo? = requestInfoBuilder.build(fillRequest)

        if (requestInfo == null) {
            fillCallback.onFailure(null)
            return
        }

        val clientState: Bundle = (fillRequest.clientState ?: Bundle())
        requestInfo += clientState.getRequestInfo()
        clientState.putRequestInfo(requestInfo)

        sendFillResponse(requestInfo, clientState, fillCallback)
    }

    private fun sendFillResponse(requestInfo: RequestInfo, clientState: Bundle, fillCallback: FillCallback) {
        if (autofillAuthProvider.isAuthRequired) {
            sendLockedFillResponse(requestInfo, clientState, fillCallback)
        } else {
            sendUnlockedFillResponse(requestInfo, clientState, fillCallback)
        }
    }

    private fun sendLockedFillResponse(requestInfo: RequestInfo, clientState: Bundle, fillCallback: FillCallback) {
        val authIntentSender: IntentSender = autofillAuthProvider.getAuthIntentSenderForFill(clientState)

        val lockedFillResponse: FillResponse =
            fillResponseProducer.produceLockedFillResponse(authIntentSender, requestInfo, clientState)

        fillCallback.onSuccess(lockedFillResponse)
    }

    private fun sendUnlockedFillResponse(requestInfo: RequestInfo, clientState: Bundle, fillCallback: FillCallback) {
        disposable = fillResponseProducer
            .produceUnlockedFillResponse(requestInfo, clientState)
            .subscribe(
                { fillResponseResource: FillResponseResource ->
                    when (fillResponseResource) {
                        is Resource.Success -> {
                            fillCallback.onSuccess(fillResponseResource.data)
                        }
                        is Resource.Loading -> Unit
                        is Resource.Error -> {
                            fillCallback.onFailure(
                                context.getString(R.string.faf_fill_failure)
                            )
                        }
                    }
                    unsubscribe()
                },
                { error: Throwable ->
                    fillCallback.onFailure(context.getString(R.string.faf_fill_failure))
                    unsubscribe()
                }
            )
    }

    private fun unsubscribe() {
        disposable?.dispose()
        disposable = null
    }
}