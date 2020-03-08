package com.eakurnikov.autoque.autofill.impl.internal.domain.verification

/**
 * Created by eakurnikov on 2019-09-14
 */
interface AutofillClientVerifier {

    fun isInstallerSafe(clientPackageName: String): Boolean

    fun isForbidden(clientPackageName: String): Boolean
}