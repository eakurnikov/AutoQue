package com.eakurnikov.common.data

/**
 * Created by eakurnikov on 2019-09-15
 */
sealed class Resource<T> {

    class Success<T>(val data: T) : Resource<T>()

    class Loading<T>(val data: T? = null) : Resource<T>()

    class Error<T>(val message: String, val data: T? = null) : Resource<T>()
}