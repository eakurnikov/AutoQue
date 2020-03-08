package com.eakurnikov.autoque.autofill.impl.internal.viewmodel.viewall

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.autofill.AutofillManager
import androidx.lifecycle.ViewModel
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.AutofillPayload
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.autoque.autofill.impl.internal.data.enums.FillDataType
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataResource
import com.eakurnikov.autoque.autofill.impl.internal.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.domain.response.DatasetBuilder
import com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.showall.ProduceFillDataEntitiesUseCase
import com.eakurnikov.autoque.autofill.impl.internal.extensions.getRequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.extensions.log
import com.eakurnikov.autoque.autofill.impl.internal.extensions.rankByPlainText
import com.eakurnikov.autoque.autofill.impl.internal.ui.autofill.AutofillUi
import com.eakurnikov.autoque.autofill.impl.internal.ui.viewall.ViewAllUi
import com.eakurnikov.common.annotations.AppContext
import com.eakurnikov.common.data.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-16
 */
@TargetApi(Build.VERSION_CODES.O)
class ViewAllViewModel @Inject constructor(
    @AppContext private val context: Context,
    private val produceFillDataEntitiesUseCase: ProduceFillDataEntitiesUseCase,
    private val datasetBuilder: DatasetBuilder,
    private val autofillUi: AutofillUi
) : ViewModel() {

    private val tag: String = "ViewAllViewModel"

    private var viewAllRequestInfo: RequestInfo? = null

    private var cachedFillDataDtos: List<FillDataDto> = emptyList()

    private val fillDataSubject: BehaviorSubject<Resource<List<FillDataDto>>> =
        BehaviorSubject.createDefault(Resource.Loading<List<FillDataDto>>(null))

    fun getFillDataSubject(): BehaviorSubject<Resource<List<FillDataDto>>> = fillDataSubject

    fun onAbleToShow(
        viewAllUi: ViewAllUi,
        viewAllPayload: AutofillPayload?
    ) {
        fillDataSubject.onNext(Resource.Loading<List<FillDataDto>>(null))

        if (viewAllPayload == null) {
            log("$tag: Show all payload is null")
            viewAllUi.finish()
            autofillUi.showAsToast(R.string.faf_fill_failure)
            return
        }

        val localViewAllRequestInfo: RequestInfo? = viewAllPayload.clientState.getRequestInfo()

        if (localViewAllRequestInfo == null) {
            log("$tag: Show all request info is null")
            viewAllUi.finish()
            autofillUi.showAsToast(R.string.faf_fill_failure)
            return
        }

        viewAllRequestInfo = localViewAllRequestInfo

        produceFillDataEntitiesUseCase
            .invoke(localViewAllRequestInfo, viewAllPayload.clientState)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(OnFillDataDtosProduced())
    }

    fun onSearch(text: String) {
        if (text.isEmpty()) {
            return fillDataSubject.onNext(Resource.Success(cachedFillDataDtos))
        }
        val ranked: List<FillDataDto> =
            cachedFillDataDtos
                .filter { fillDataDto: FillDataDto ->
                    with(fillDataDto) {
                        account.name.contains(text) ||
                                account.packageName.contains(text) ||
                                login.login.contains(text)
                    }
                }
                .rankByPlainText(text)
        fillDataSubject.onNext(Resource.Success(ranked))
    }

    fun onSelected(viewAllUi: ViewAllUi, id: Int) {
        viewAllRequestInfo ?: return
        val resource: Resource<List<FillDataDto>> = fillDataSubject.value ?: return
        when (resource) {
            is Resource.Success -> {
                val dataset = datasetBuilder.buildUnlocked(
                    viewAllRequestInfo!!.screenInfo.authFormInfo,
                    resource.data[id]
                )
                val resultIntent: Intent = Intent().apply {
                    putExtra(AutofillManager.EXTRA_AUTHENTICATION_RESULT, dataset)
                }
                viewAllUi.activityContext.setResult(Activity.RESULT_OK, resultIntent)
            }
        }
        viewAllUi.finish()
    }

    private inner class OnFillDataDtosProduced : DisposableSingleObserver<FillDataResource>() {

        override fun onSuccess(resource: FillDataResource) {
            when (resource.type) {
                FillDataType.LOCKED -> {
                    resource.intentSender
                        ?.sendIntent(context, 0, null, null, null)
                        ?: log("$tag: Auth intent for fill data is null somehow")
                }
                FillDataType.UNLOCKED -> {
                    if (resource.fillData == null) {
                        log("$tag: Fill data is null somehow")
                    } else {
                        cachedFillDataDtos = resource.fillData
                        fillDataSubject.onNext(Resource.Success(resource.fillData))
                    }
                }
            }
            dispose()
        }

        override fun onError(error: Throwable) {
            log("$tag: Error while producing fill data entities: $error", error)
            fillDataSubject.onNext(Resource.Error(error.message ?: "Error", null))
            dispose()
        }
    }
}