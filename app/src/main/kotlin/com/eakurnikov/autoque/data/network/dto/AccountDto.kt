package com.eakurnikov.autoque.data.network.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by eakurnikov on 2019-12-15
 */
data class AccountDto(

    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val name: String,

    @SerializedName("body")
    val packageName: String
)