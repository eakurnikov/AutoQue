package com.eakurnikov.autoque.data.network.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by eakurnikov on 2019-12-15
 */
data class CredentialsDto(

    @SerializedName("id")
    val id: Long,

    @SerializedName("website")
    val name: String,

    @SerializedName("email")
    val login: String,

    @SerializedName("gender")
    val password: String,

    @SerializedName("address")
    val comment: String,

    @SerializedName("first_name")
    val packageName: String
)

/*
{
    "id":"4",
    "first_name":"Norris",
    "last_name":"Russel",
    "gender":"male",
    "dob":"1959-06-17",
    "email":"bahringer.gregorio@example.net",
    "phone":"(991) 498-0367 x5855",
    "website":"https://www.skiles.biz/modi-aut-molestiae-animi-explicabo-illum-id-nihil-est",
    "address":"1155 Maggie Passage\nPort Collinmouth, MT 24126-8745",
    "status":"inactive",
    "_links":{
        "self":{
            "href":"https://gorest.co.in/public-api/users/4"
        },
        "edit":{
            "href":"https://gorest.co.in/public-api/users/4"
        },
        "avatar":{
            "href":"https://lorempixel.com/250/250/people/?30203"
        }
    }
}
 */