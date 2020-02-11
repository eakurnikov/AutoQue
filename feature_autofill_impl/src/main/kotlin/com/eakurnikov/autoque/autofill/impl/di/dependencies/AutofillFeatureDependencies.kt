package com.eakurnikov.autoque.autofill.impl.di.dependencies

import android.content.Context
import com.eakurnikov.autoque.autofill.api.dependencies.domain.dal.DigitalAssetLinksVerifier
import com.eakurnikov.autoque.autofill.api.dependencies.data.dao.AutofillDao
import com.eakurnikov.autoque.autofill.api.dependencies.domain.auth.AutofillAuthProvider
import com.eakurnikov.autoque.autofill.api.dependencies.domain.disclaimer.AutofillDisclaimerProvider
import com.eakurnikov.autoque.autofill.api.dependencies.domain.update.AutofillUpdatePromptProvider
import com.eakurnikov.autoque.autofill.api.dependencies.domain.verification.AutofillClientVerifier
import com.eakurnikov.common.annotations.AppContext

/**
 * Created by eakurnikov on 2019-09-15
 */
interface AutofillFeatureDependencies {

    @AppContext
    fun context(): Context

    fun autofillDao(): AutofillDao

    fun autofillClientVerifier(): AutofillClientVerifier

    fun digitalAssetLinksVerifier(): DigitalAssetLinksVerifier

    fun autofillAuthProvider(): AutofillAuthProvider<*>

    fun autofillDisclaimerProvider(): AutofillDisclaimerProvider<*>

    fun autofillUpdatePromptProvider(): AutofillUpdatePromptProvider<*>
}