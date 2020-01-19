package com.eakurnikov.autoque.data.network.api

import com.eakurnikov.autoque.data.network.dto.AccountDto
import com.eakurnikov.autoque.data.network.dto.LoginDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by eakurnikov on 2019-12-15
 */
interface CredentialsApi {

    @GET("/posts")
    fun getAccounts(): Single<List<AccountDto>>

    @GET("/comments")
    fun getLogins(): Single<List<LoginDto>>

    @GET("/comments")
    fun getLoginsForAccount(@Query("postId") accountId: Long): Single<List<LoginDto>>?
}