package com.eakurnikov.autoque.autofill.impl.internal.data.model

import android.content.IntentSender
import android.os.Parcelable
import android.service.autofill.Dataset
import android.service.autofill.FillResponse
import android.view.autofill.AutofillId
import com.eakurnikov.autoque.autofill.impl.internal.data.enums.FillDataType
import com.eakurnikov.autoque.autofill.impl.internal.data.enums.FillResponseType
import com.eakurnikov.autoque.autofill.impl.internal.data.enums.IntentSenderType
import com.eakurnikov.autoque.autofill.impl.internal.data.enums.UnsafeDatasetType
import kotlinx.android.parcel.Parcelize

/**
 * Created by eakurnikov on 2019-09-15
 */
@Parcelize
data class ViewInfo(
    val autofillId: AutofillId,
    val autofillType: Int,
    val autofillHint: String,
    val autofillValue: String?,
    val saveType: Int,
    val isFocused: Boolean
) : Parcelable

@Parcelize
data class AuthFormInfo(
    val login: ViewInfo?,
    val password: ViewInfo?
) : Parcelable

@Parcelize
data class ScreenInfo(
    val authFormInfo: AuthFormInfo
) : Parcelable

@Parcelize
data class RequestInfo(
    val requestIds: List<Int>,
    val clientPackageName: String,
    val screenInfo: ScreenInfo
) : Parcelable

@Parcelize
data class IntentSenderResource(
    val intentSender: IntentSender,
    val type: IntentSenderType
) : Parcelable

@Parcelize
data class FillResponseResource(
    val fillResponse: FillResponse,
    val type: FillResponseType
) : Parcelable

@Parcelize
data class UnsafeDatasetResource(
    val dataset: Dataset?,
    val intentSender: IntentSender?,
    val type: UnsafeDatasetType
) : Parcelable

data class FillDataResource(
    val fillData: List<FillDataDto>?,
    val intentSender: IntentSender?,
    val type: FillDataType
)