package com.eakurnikov.autoque.di.modules.common

import android.util.Log
import com.eakurnikov.autoque.data.network.NetworkConstants
import com.eakurnikov.autoque.data.network.interceptors.AuthInterceptor
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by eakurnikov on 2019-10-07
 */
@Module
class NetworkModule {

    @Provides
    @AppScope
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger { Log.i(NetworkConstants.NETWORK_LOG_TAG, it) }
        ).apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    @AppScope
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor()

    @Provides
    @AppScope
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(NetworkConstants.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(NetworkConstants.CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @AppScope
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
            )
            .client(okHttpClient)
            .build()
    }
}