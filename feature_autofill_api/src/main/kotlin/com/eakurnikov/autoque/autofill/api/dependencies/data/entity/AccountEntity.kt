package com.eakurnikov.autoque.autofill.api.dependencies.data.entity

/**
 * Created by eakurnikov on 2019-09-14
 */
interface AccountEntity {
    val id: Long?
    val name: String
    val comment: String?
    val packageName: String
}