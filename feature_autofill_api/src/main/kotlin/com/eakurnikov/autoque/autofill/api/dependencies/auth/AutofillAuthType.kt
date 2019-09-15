package com.eakurnikov.autoque.autofill.api.dependencies.auth

/**
 * Created by eakurnikov on 2019-09-14
 */
enum class AutofillAuthType(
    private val mDescription: String
) {
    FILL("FILL"),
    SAVE("SAVE");

    override fun toString(): String = mDescription
}