package com.eakurnikov.autoque.autofill.impl.extensions

import com.eakurnikov.autoque.autofill.impl.data.model.FillDataEntity

/**
 * Created by eakurnikov on 2019-09-15
 */

fun List<FillDataEntity>.sort(targetPackageName: String): List<FillDataEntity> {
    if (isEmpty()) return this

    val withExactPackageName: List<FillDataEntity> = filter { it.accountEntity.packageName == targetPackageName }

    if (withExactPackageName.isNotEmpty()) return withExactPackageName.sortedBy { it.accountEntity.name }

    return sortedBy { it.accountEntity.name }
        .map { Pair(it, it.accountEntity.packageName.count { targetPackageName.contains(it) }) }
        .sortedByDescending { it.second }
        .map { it.first }
}

fun List<FillDataEntity>.truncate(limit: Int): List<FillDataEntity> = if (size > limit) subList(0, limit) else this

fun List<FillDataEntity>.containsEntityWithPackageName(targetPackageName: String): Boolean =
    find { it.accountEntity.packageName == targetPackageName } != null