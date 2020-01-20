package com.eakurnikov.autoque.data.network.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by eakurnikov on 2019-12-15
 */
data class ResponseByIdDto(

    @SerializedName("result")
    val result: CredentialsDto
)