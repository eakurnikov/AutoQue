package com.eakurnikov.autoque.autofill.impl.internal.extensions

import com.eakurnikov.autoque.autofill.impl.internal.data.model.ViewAllItem

/**
 * Created by eakurnikov on 2019-12-15
 */
fun List<ViewAllItem>.getFillDataHolderById(id: Int): ViewAllItem.FillDataHolder? =
    when (val item: ViewAllItem = get(id)) {
        is ViewAllItem.Section -> null
        is ViewAllItem.FillDataHolder -> item
    }