package com.eakurnikov.autoque.autofill.api.dependencies.data.entity

/**
 * Created by eakurnikov on 2019-09-14
 */
interface LoginEntity {
    val id: Long?
    val name: String
    val login: String
    val password: String
    val comment: String?
}