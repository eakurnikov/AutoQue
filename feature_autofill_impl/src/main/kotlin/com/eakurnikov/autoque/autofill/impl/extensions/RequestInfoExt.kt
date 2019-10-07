package com.eakurnikov.autoque.autofill.impl.extensions

import com.eakurnikov.autoque.autofill.impl.data.model.AuthFormInfo
import com.eakurnikov.autoque.autofill.impl.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.data.model.ScreenInfo
import com.eakurnikov.autoque.autofill.impl.data.model.ViewInfo

/**
 * Created by eakurnikov on 2019-09-15
 */
operator fun RequestInfo?.plus(addendum: RequestInfo?): RequestInfo? {
    this ?: addendum ?: return null

    if (this == null) return addendum
    if (addendum == null) return this

    if (clientPackageName != addendum.clientPackageName) return null
    if (screenInfo.webDomain != addendum.screenInfo.webDomain) return null

    val resultLogin: ViewInfo? = screenInfo.authFormInfo.login ?: addendum.screenInfo.authFormInfo.login
    val resultPassword: ViewInfo? = screenInfo.authFormInfo.password ?: addendum.screenInfo.authFormInfo.password
    val resultRequestIds: List<Int> = requestIds + addendum.requestIds

    return RequestInfo(
        resultRequestIds,
        clientPackageName,
        ScreenInfo(
            AuthFormInfo(resultLogin, resultPassword),
            screenInfo.webDomain
        )
    )
}

fun RequestInfo.isValid(): Boolean = with(screenInfo.authFormInfo) {
    login?.autofillValue != null && password?.autofillValue != null
}