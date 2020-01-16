package com.eakurnikov.autoque.autofill.impl.internal.domain.filldata

import android.annotation.TargetApi
import android.os.Build
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.Account
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.Login
import com.eakurnikov.autoque.autofill.impl.internal.data.model.AccountDto
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto
import com.eakurnikov.autoque.autofill.impl.internal.data.model.LoginDto
import com.eakurnikov.autoque.autofill.impl.internal.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.domain.clientapp.AppInfoProvider
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-09
 *
 * Builds and [FillDataDto] from [SaveRequestInfo] for saving.
 */
@TargetApi(Build.VERSION_CODES.O)
class FillDataBuilder @Inject constructor(
    private val appInfoProvider: AppInfoProvider
) {
    fun build(saveRequestInfo: RequestInfo): FillDataDto {
        with(saveRequestInfo) {
            val clientAppName: String = appInfoProvider.provideAppName(clientPackageName)
            val login: String = screenInfo.authFormInfo.login!!.autofillValue!!
            val password: String = screenInfo.authFormInfo.password!!.autofillValue!!

            val accountDto: Account = AccountDto(clientAppName, clientPackageName)
            val loginDto: Login = LoginDto(login, password)

            return FillDataDto(accountDto, loginDto)
        }
    }
}