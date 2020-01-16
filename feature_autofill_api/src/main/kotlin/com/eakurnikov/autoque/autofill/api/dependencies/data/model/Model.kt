package com.eakurnikov.autoque.autofill.api.dependencies.data.model

/**
 * Created by eakurnikov on 2019-09-14
 */
interface Account {
    val id: Long?
    val name: String
    val comment: String?
    val packageName: String
}

interface Login {
    val id: Long?
    val login: String
    val password: String
    val comment: String?
}