package com.eakurnikov.common.util

/**
 * Created by eakurnikov on 2020-01-16
 */

inline fun <reified T, R> T.getPrivateField(fieldName: String): R? =
    T::class.java
        .getDeclaredField(fieldName)
        .apply { isAccessible = true }
        .get(this) as R?

inline fun <reified T, R> T.setPrivateField(fieldName: String, value: R): Unit =
    T::class.java
        .getDeclaredField(fieldName)
        .apply { isAccessible = true }
        .set(this, value)

@JvmName("setPrivateFieldJava")
fun <R> setPrivateField(obj: Any, fieldName: String, value: R): Unit =
    obj.setPrivateField(fieldName, value)