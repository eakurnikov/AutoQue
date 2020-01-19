package com.eakurnikov.autoque.data.network.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by eakurnikov on 2019-12-15
 */
data class LoginDto(

    @SerializedName("id")
    val id: Long,

    @SerializedName("postId")
    val accountId: Long,

    @SerializedName("email")
    val login: String,

    @SerializedName("name")
    val password: String,

    @SerializedName("body")
    val comment: String
)