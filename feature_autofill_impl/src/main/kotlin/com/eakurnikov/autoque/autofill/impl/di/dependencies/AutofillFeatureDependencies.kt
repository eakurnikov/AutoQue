package com.eakurnikov.autoque.autofill.impl.di.dependencies

import android.content.Context
import com.eakurnikov.autoque.autofill.api.dependencies.auth.AuthProvider
import com.eakurnikov.autoque.autofill.api.dependencies.dal.DigitalAssetLinksVerifier
import com.eakurnikov.autoque.autofill.api.dependencies.data.dao.AutofillDao
import com.eakurnikov.autoque.autofill.api.dependencies.packagename.PackageVerifier
import com.eakurnikov.common.annotations.AppContext

/**
 * Created by eakurnikov on 2019-09-15
 */
interface AutofillFeatureDependencies {

    @AppContext
    fun context(): Context

    fun autofillDao(): AutofillDao

    fun packageVerificator(): PackageVerifier

    fun digitalAssetLinksVerifier(): DigitalAssetLinksVerifier

    fun autofillAuthProvider(): AuthProvider<*>
}