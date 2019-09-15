package com.eakurnikov.autoque.autofill.impl.extensions

import android.os.Bundle
import com.eakurnikov.autoque.autofill.impl.data.model.AuthFormInfo
import com.eakurnikov.autoque.autofill.impl.data.model.RequestInfo

/**
 * Created by eakurnikov on 2019-09-15
 */

private const val LOGIN_KEY = "LOGIN"
private const val PASSWORD_KEY = "PASSWORD"
private const val LOGIN_AND_PASSWORD_KEY = "LOGIN_AND_PASSWORD"

fun Bundle.putRequestInfo(requestInfo: RequestInfo) {
    classLoader = RequestInfo::class.java.classLoader
    val authFormInfo: AuthFormInfo = requestInfo.screenInfo.authFormInfo
    when {
        authFormInfo.password == null -> putParcelable(LOGIN_KEY, requestInfo)
        authFormInfo.login == null -> putParcelable(PASSWORD_KEY, requestInfo)
        else -> putParcelable(LOGIN_AND_PASSWORD_KEY, requestInfo)
    }
}

fun Bundle.getRequestInfo(): RequestInfo? {
    classLoader = RequestInfo::class.java.classLoader
    getParcelable<RequestInfo>(LOGIN_AND_PASSWORD_KEY)?.let { return it }
    return getParcelable<RequestInfo>(LOGIN_KEY) + getParcelable<RequestInfo>(PASSWORD_KEY)
}