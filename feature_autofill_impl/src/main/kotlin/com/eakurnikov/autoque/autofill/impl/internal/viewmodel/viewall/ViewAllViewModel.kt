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
import com.eakurnikov.autoque.autofill.impl.internal.data.model.ViewAllItem
import com.eakurnikov.autoque.autofill.impl.internal.domain.response.DatasetBuilder
import com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.viewall.ProduceFillDataEntitiesUseCase
import com.eakurnikov.autoque.autofill.impl.internal.extensions.*
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

    private var fillDataDtos: List<FillDataDto> = emptyList()

    private val viewAllItemsSubject: BehaviorSubject<Resource<List<ViewAllItem>>> =
        BehaviorSubject.createDefault(Resource.Loading<List<ViewAllItem>>(null))

    private val relevantSectionTitle: String =
        context.resources.getString(R.string.faf_view_all_relevant)

    private val otherSectionTitle: String =
        context.resources.getString(R.string.faf_view_all_other)

    fun getViewAllItemSubject(): BehaviorSubject<Resource<List<ViewAllItem>>> =
        viewAllItemsSubject

    fun onAbleToShow(viewAllUi: ViewAllUi, viewAllPayload: AutofillPayload?) {
        viewAllItemsSubject.onNext(Resource.Loading(null))

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
        val viewAllItems: List<ViewAllItem> = when {
            text.isEmpty() -> {
                fillDataDtos.toViewAllItems(relevantSectionTitle, otherSectionTitle)
            }
            else -> {
                fillDataDtos
                    .filter { fillDataDto: FillDataDto ->
                        with(fillDataDto) {
                            account.name.contains(text) ||
                                    account.packageName.contains(text) ||
                                    login.login.contains(text)
                        }
                    }
                    .rankByPlainText(text)
                    .wrapWithHolders()
            }
        }
        viewAllItemsSubject.onNext(Resource.Success(viewAllItems))
    }

    fun onSelected(viewAllUi: ViewAllUi, id: Int) {
        val resource: Resource<List<ViewAllItem>> = viewAllItemsSubject.value ?: return
        when (resource) {
            is Resource.Success -> {
                viewAllRequestInfo?.let { requestInfo: RequestInfo ->
                    resource.data.getFillDataHolderById(id)?.let { fillDataHolder ->
                        val dataset = datasetBuilder.buildUnlocked(
                            requestInfo.screenInfo.authFormInfo,
                            fillDataHolder.fillDataDto
                        )
                        val resultIntent: Intent = Intent().apply {
                            putExtra(AutofillManager.EXTRA_AUTHENTICATION_RESULT, dataset)
                        }
                        viewAllUi.activityContext.setResult(Activity.RESULT_OK, resultIntent)
                    }
                }
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
                        /**temporay stub**/
                        val fillData: List<FillDataDto> =
                            resource.fillData.mapIndexed { index: Int, dto: FillDataDto ->
                                if (index < 3) {
                                    dto.isRelevant = true
                                }
                                dto
                            }
                        val viewAllItems: List<ViewAllItem> =
                            fillData.toViewAllItems(
                                relevantSectionTitle,
                                otherSectionTitle
                            )
                        fillDataDtos = fillData
                        viewAllItemsSubject.onNext(Resource.Success(viewAllItems))
                    }
                }
            }
            dispose()
        }

        override fun onError(error: Throwable) {
            log("$tag: Error while producing fill data entities: $error", error)
            viewAllItemsSubject.onNext(Resource.Error(error.message ?: "Error", null))
            dispose()
        }
    }
}