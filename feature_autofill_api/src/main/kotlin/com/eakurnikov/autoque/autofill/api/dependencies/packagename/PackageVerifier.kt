package com.eakurnikov.autoque.autofill.api.dependencies.packagename

/**
 * Created by eakurnikov on 2019-09-14
 */
interface PackageVerifier {

    fun verifyPackage(clientPackageName: String): Boolean
}