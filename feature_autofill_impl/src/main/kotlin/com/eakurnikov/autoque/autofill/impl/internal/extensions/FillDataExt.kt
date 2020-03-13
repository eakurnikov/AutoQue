package com.eakurnikov.autoque.autofill.impl.internal.extensions

import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto
import com.eakurnikov.autoque.autofill.impl.internal.data.model.ViewAllItem
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by eakurnikov on 2019-09-15
 */
fun List<FillDataDto>.rankByPackageName(targetPackageName: String): List<FillDataDto> {
    if (isEmpty()) return this

    val withExactPackageName: List<FillDataDto> = this
        .filter { it.account.packageName == targetPackageName }
        .map { it.apply { isRelevant = true } }
        .sortedBy { it.account.name }

    return if (withExactPackageName.isNotEmpty()) {
        withExactPackageName
    } else {
        this.internalSort(targetPackageName.substringAfter("."))
    }
}

fun List<FillDataDto>.rankByPlainText(text: String): List<FillDataDto> {
    if (isEmpty()) return this

    val withExactText: List<FillDataDto> = this
        .filter { it.account.packageName == text }
        .sortedBy { it.account.name }

    return if (withExactText.isNotEmpty()) {
        withExactText
    } else {
        this.internalSort(text)
    }
}

fun List<FillDataDto>.sortByPackageName(targetPackageName: String): List<FillDataDto> {
    if (isEmpty()) return this

    val withExactPackageName: List<FillDataDto> = this
        .filter { it.account.packageName == targetPackageName }
        .map { it.apply { isRelevant = true } }
        .sortedBy { it.account.name }

    val other: List<FillDataDto> = this
        .filter { !it.isRelevant }
        .internalSort(targetPackageName.substringAfter("."))

    return withExactPackageName + other
}

fun List<FillDataDto>.truncate(limit: Int): List<FillDataDto> =
    if (size > limit) subList(0, limit) else this

fun List<FillDataDto>.wrapWithHolders(): List<ViewAllItem.FillDataHolder> =
    map { ViewAllItem.FillDataHolder(it) }

fun List<FillDataDto>.toViewAllItems(
    suitableSectionTitle: String,
    otherSectionTitle: String
): List<ViewAllItem> {

    val fillDataHolders: List<ViewAllItem.FillDataHolder> = wrapWithHolders()
    val divideIndex: Int = indexOfFirst { !it.isRelevant }

    return when {
        divideIndex > 0 -> {
            ArrayList<ViewAllItem>(fillDataHolders.size + 2).apply {
                add(ViewAllItem.Section(suitableSectionTitle))
                addAll(fillDataHolders.subList(0, divideIndex))
                add(ViewAllItem.Section(otherSectionTitle))
                addAll(fillDataHolders.subList(divideIndex, fillDataHolders.size))
            }
        }
        else -> {
            fillDataHolders
        }
    }
}

private fun List<FillDataDto>.internalSort(pivot: String): List<FillDataDto> {
    return sortedBy { it.account.name }
        .map { it to it.rank(pivot) }
        .sortedByDescending { it.second }
        .map { it.first }
}

private fun FillDataDto.rank(extractedPackageName: String): Int =
    account.packageName.getRankAsPackageName(extractedPackageName) +
            account.name.getRankAsName(extractedPackageName)

private fun String.getRankAsName(extractedPackageName: String): Int =
    count { it in extractedPackageName }

private fun String.getRankAsPackageName(extractedPackageName: String): Int =
    substringAfter(".").count { it in extractedPackageName }

private fun String.getRankAsUrl(extractedPackageName: String): Int =
    try {
        URL(this).host
    } catch (e: MalformedURLException) {
        this
    }
        .removePrefix("www.")
        .substringBeforeLast(".")
        .count { it in extractedPackageName }

//fun List<FillDataDto>.rankByPackageName(targetPackageName: String): List<FillDataDto> {
//    if (isEmpty()) return this
//
//    val withExactPackageName: List<FillDataDto> =
//        filter { it.account.packageName == targetPackageName }
//
//    if (withExactPackageName.isNotEmpty()) {
//        return withExactPackageName.sortedBy { it.account.name }
//    }
//
//    return internalSort(targetPackageName)
//}

//fun List<FillDataDto>.rankByPlainText(text: String): List<FillDataDto> {
//    if (isEmpty()) return this
//
//    val withExactText: List<FillDataDto> =
//        filter { it.account.packageName == text }
//
//    if (withExactText.isNotEmpty()) {
//        return withExactText.sortedBy { it.account.name }
//    }
//
//    return internalSort(text)
//}