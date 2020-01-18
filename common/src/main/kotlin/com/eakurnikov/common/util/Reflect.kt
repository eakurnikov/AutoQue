package com.eakurnikov.common.util

/**
 * Created by eakurnikov on 2020-01-16
 */

fun Any.getPrivateField(fieldName: String): Any? =
    javaClass
        .getDeclaredField(fieldName)
        .apply { isAccessible = true }
        .get(this)

fun Any.setPrivateField(fieldName: String, value: Any): Unit =
    javaClass
        .getDeclaredField(fieldName)
        .apply { isAccessible = true }
        .set(this, value)

@JvmName("setPrivateFieldJava")
fun setPrivateField(obj: Any, fieldName: String, value: Any): Unit =
    obj.setPrivateField(fieldName, value)