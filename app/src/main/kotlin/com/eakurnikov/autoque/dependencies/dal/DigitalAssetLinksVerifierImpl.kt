package com.eakurnikov.autoque.dependencies.dal

import com.eakurnikov.autoque.autofill.api.dependencies.dal.DigitalAssetLinksVerifier
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 */
class DigitalAssetLinksVerifierImpl
@Inject constructor() : DigitalAssetLinksVerifier {

    /**
     * A temporary stub.
     */
    override fun verify(): Boolean = true
}