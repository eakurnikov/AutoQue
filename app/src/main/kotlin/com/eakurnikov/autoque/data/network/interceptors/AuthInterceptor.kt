package com.eakurnikov.autoque.data.network.interceptors

import com.eakurnikov.autoque.data.network.NetworkConstants.ACCESS_TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $ACCESS_TOKEN")
                .build()
        )
    }
}