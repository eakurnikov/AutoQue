package com.eakurnikov.autoque.autofill.impl.internal.extensions

import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto

/**
 * Created by eakurnikov on 2019-09-15
 */
fun List<FillDataDto>.rankByPackageName(targetPackageName: String): List<FillDataDto> {
    if (isEmpty()) return this

    val withExactPackageName: List<FillDataDto> =
        filter { it.account.packageName == targetPackageName }

    if (withExactPackageName.isNotEmpty()) {
        return withExactPackageName.sortedBy { it.account.name }
    }

    return internalSort(targetPackageName)
}

fun List<FillDataDto>.rankByPlainText(text: String): List<FillDataDto> {
    if (isEmpty()) return this

    val withExactText: List<FillDataDto> =
        filter { it.account.packageName == text }

    if (withExactText.isNotEmpty()) {
        return withExactText.sortedBy { it.account.name }
    }

    return internalSort(text)
}

fun FillDataDto.rank(targetPackageName: String): Int =
    account.packageName.count { targetPackageName.contains(it) }

fun List<FillDataDto>.truncate(limit: Int): List<FillDataDto> =
    if (size > limit) subList(0, limit) else this

fun List<FillDataDto>.containsEntityWithPackageName(targetPackageName: String): Boolean =
    find { it.account.packageName == targetPackageName } != null

private fun List<FillDataDto>.internalSort(pivot: String): List<FillDataDto> {
    return sortedBy { it.account.name }
        .map { it to it.rank(pivot) }
        .sortedByDescending { it.second }
        .map { it.first }
}