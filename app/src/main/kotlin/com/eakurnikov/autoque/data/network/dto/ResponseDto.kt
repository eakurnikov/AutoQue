package com.eakurnikov.autoque.data.network.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by eakurnikov on 2019-12-15
 */
data class ResponseDto(

    @SerializedName("result")
    val result: List<CredentialsDto>
)

/*
{
    "_meta":{
        "success":true,
        "code":200,
        "message":"OK. Everything worked as expected.",
        "totalCount":2213,
        "pageCount":111,
        "currentPage":1,
        "perPage":20,
        "rateLimit":{
            "limit":30,
            "remaining":29,
            "reset":2
        }
    },
    "result":[
        {...},
        {...},
        ...
    ]
}
 */