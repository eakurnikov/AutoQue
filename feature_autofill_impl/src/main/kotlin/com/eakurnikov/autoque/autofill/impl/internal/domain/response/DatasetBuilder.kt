package com.eakurnikov.autoque.autofill.impl.internal.domain.response

import android.annotation.TargetApi
import android.content.IntentSender
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.service.autofill.Dataset
import android.view.autofill.AutofillValue
import android.widget.RemoteViews
import com.eakurnikov.autoque.autofill.impl.internal.data.model.AuthFormInfo
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto
import com.eakurnikov.autoque.autofill.impl.internal.domain.clientapp.AppInfoProvider
import com.eakurnikov.autoque.autofill.impl.internal.domain.providers.disclaimer.DisclaimerProvider
import com.eakurnikov.autoque.autofill.impl.internal.extensions.putFillDataId
import com.eakurnikov.autoque.autofill.impl.internal.ui.autofill.AutofillViewBuilder
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-09
 *
 * Builds a [Dataset] using [AutofillViewBuilder] and [AppInfoProvider]. Works only with login and
 * password values.
 */
@TargetApi(Build.VERSION_CODES.O)
class DatasetBuilder @Inject constructor(
    private val appInfoProvider: AppInfoProvider,
    private val autofillViewBuilder: AutofillViewBuilder,
    private val disclaimerProvider: DisclaimerProvider
) {
    fun buildUnlocked(authFormInfo: AuthFormInfo, fillDataDto: FillDataDto): Dataset {
        val appIcon: Bitmap? =
            appInfoProvider.provideAppIconAsBitmap(fillDataDto.account.packageName)

        val datasetItemView: RemoteViews =
            autofillViewBuilder.buildDatasetItemView(
                fillDataDto.login.login,
                fillDataDto.account.name,
                appIcon
            )

        return Dataset
            .Builder(datasetItemView)
            .setLogin(authFormInfo, fillDataDto)
            .setPassword(authFormInfo, fillDataDto)
            .build()
    }

    fun buildLocked(
        authFormInfo: AuthFormInfo,
        fillDataDto: FillDataDto,
        clientState: Bundle
    ): Dataset {
        val appIcon: Bitmap? =
            appInfoProvider.provideAppIconAsBitmap(fillDataDto.account.packageName)

        val datasetItemView: RemoteViews =
            autofillViewBuilder.buildDatasetItemView(
                fillDataDto.login.login,
                fillDataDto.account.name,
                appIcon
            )

        val disclaimerIntentSender: IntentSender =
            disclaimerProvider.getDisclaimerIntentSender(
                clientState.apply { putFillDataId(fillDataDto.id) }
            )

        return Dataset
            .Builder(datasetItemView)
            .setAuthentication(disclaimerIntentSender)
            .setLogin(authFormInfo, null)
            .setPassword(authFormInfo, null)
            .build()
    }

    fun buildUnsafe(authFormInfo: AuthFormInfo, fillDataDto: FillDataDto): Dataset {
        return Dataset
            .Builder(autofillViewBuilder.buildDatasetUnsafeItemView())
            .setLogin(authFormInfo, fillDataDto)
            .setPassword(authFormInfo, fillDataDto)
            .build()
    }

    private fun Dataset.Builder.setLogin(
        authFormInfo: AuthFormInfo,
        fillDataDto: FillDataDto?
    ): Dataset.Builder {

        authFormInfo.login ?: return this

        return setValue(
            authFormInfo.login.autofillId,
            AutofillValue.forText(fillDataDto?.login?.login)
        )
    }

    private fun Dataset.Builder.setPassword(
        authFormInfo: AuthFormInfo,
        fillDataDto: FillDataDto?
    ): Dataset.Builder {

        authFormInfo.password ?: return this

        return setValue(
            authFormInfo.password.autofillId,
            AutofillValue.forText(fillDataDto?.login?.password)
        )
    }
}