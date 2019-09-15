package com.eakurnikov.autoque.autofill.impl.domain.request.fill

import android.graphics.Bitmap
import android.service.autofill.Dataset
import android.view.autofill.AutofillValue
import android.widget.RemoteViews
import com.eakurnikov.autoque.autofill.impl.data.model.AuthFormInfo
import com.eakurnikov.autoque.autofill.impl.data.model.FillDataEntity
import com.eakurnikov.autoque.autofill.impl.domain.clientapp.AppInfoProvider
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 *
 * Builds a [Dataset] using [AutofillViewProducer] and [AppInfoProvider]. Works only with login and
 * password values.
 */
class DatasetBuilder @Inject constructor(
    private val autofillViewProducer: AutofillViewProducer,
    private val appInfoProvider: AppInfoProvider
) {
    fun build(authFormInfo: AuthFormInfo, fillDataEntity: FillDataEntity): Dataset {
        val appIcon: Bitmap? = appInfoProvider.provideAppIconAsBitmap(fillDataEntity.accountEntity.packageName)

        val datasetItemView: RemoteViews =
            autofillViewProducer.datasetItemView(
                fillDataEntity.loginEntity.login,
                fillDataEntity.accountEntity.name,
                appIcon
            )

        return Dataset
            .Builder(datasetItemView)
            .setLogin(authFormInfo, fillDataEntity)
            .setPassword(authFormInfo, fillDataEntity)
            .build()
    }

    fun buildStub(authFormInfo: AuthFormInfo): Dataset {
        return Dataset
            .Builder(autofillViewProducer.noDatasetsItemView())
            .setLogin(authFormInfo, null)
            .setPassword(authFormInfo, null)
            .build()
    }

    private fun Dataset.Builder.setLogin(
        authFormInfo: AuthFormInfo,
        fillDataEntity: FillDataEntity?
    ): Dataset.Builder {

        authFormInfo.login ?: return this

        return setValue(
            authFormInfo.login.autofillId,
            AutofillValue.forText(fillDataEntity?.loginEntity?.login)
        )
    }

    private fun Dataset.Builder.setPassword(
        authFormInfo: AuthFormInfo,
        fillDataEntity: FillDataEntity?
    ): Dataset.Builder {

        authFormInfo.password ?: return this

        return setValue(
            authFormInfo.password.autofillId,
            AutofillValue.forText(fillDataEntity?.loginEntity?.password)
        )
    }
}