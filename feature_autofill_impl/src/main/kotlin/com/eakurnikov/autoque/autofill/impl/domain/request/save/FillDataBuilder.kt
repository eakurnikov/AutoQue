package com.eakurnikov.autoque.autofill.impl.domain.request.save

import android.os.Bundle
import com.eakurnikov.autoque.autofill.api.dependencies.data.entity.AccountEntity
import com.eakurnikov.autoque.autofill.api.dependencies.data.entity.LoginEntity
import com.eakurnikov.autoque.autofill.impl.data.model.AccountEntityImpl
import com.eakurnikov.autoque.autofill.impl.data.model.FillDataEntity
import com.eakurnikov.autoque.autofill.impl.data.model.LoginEntityImpl
import com.eakurnikov.autoque.autofill.impl.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.domain.clientapp.AppInfoProvider
import com.eakurnikov.autoque.autofill.impl.extensions.getRequestInfo
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 *
 * Builds and [FillDataDto] from [SaveRequestInfo] for saving.
 */
class FillDataBuilder @Inject constructor(
    private val appInfoProvider: AppInfoProvider
) {
    fun build(clientState: Bundle): FillDataEntity {
        val requestInfo: RequestInfo = clientState.getRequestInfo()!!

        with(requestInfo) {
            val clientAppName: String =
                appInfoProvider.provideAppName(clientPackageName) ?: "My account"

            val login: String = screenInfo.authFormInfo.login!!.autofillValue!!
            val password: String = screenInfo.authFormInfo.password!!.autofillValue!!

            val accountEntity: AccountEntity = AccountEntityImpl(null, clientAppName, null, clientPackageName)
            val loginEntity: LoginEntity = LoginEntityImpl(null, login, login, password, null)

            return FillDataEntity(accountEntity, loginEntity)
        }
    }
}