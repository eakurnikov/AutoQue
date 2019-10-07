package com.eakurnikov.autoque.autofill.impl.data.model

import android.os.Parcelable
import android.view.autofill.AutofillId
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
    val authFormInfo: AuthFormInfo,
    val webDomain: String
) : Parcelable

@Parcelize
data class RequestInfo(
    val requestIds: List<Int>,
    val clientPackageName: String,
    val screenInfo: ScreenInfo
) : Parcelable