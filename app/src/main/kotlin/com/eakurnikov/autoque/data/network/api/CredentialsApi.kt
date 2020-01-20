package com.eakurnikov.autoque.data.network.api

import com.eakurnikov.autoque.data.network.dto.CredentialsDto
import com.eakurnikov.autoque.data.network.dto.ResponseByIdDto
import com.eakurnikov.autoque.data.network.dto.ResponseDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by eakurnikov on 2019-12-15
 */
interface CredentialsApi {

    @GET("/public-api/users")
    fun getCredentials(): Single<ResponseDto>

    @GET("/public-api/users/{id}")
    fun getCredentialsById(
        @Path("id") credentialsId: Long
    ): Single<ResponseByIdDto>

    @GET("/public-api/users")
    fun getCredentialsByPackageName(
        @Query("first_name") packageName: String
    ): Single<ResponseDto>

    @POST("/public-api/users")
    fun saveCredentials(credentialsDto: CredentialsDto)
}