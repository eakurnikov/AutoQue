package com.eakurnikov.autoque.autofill.impl.internal.ui.viewall

import android.view.ViewGroup
import com.eakurnikov.autoque.autofill.impl.internal.data.model.ViewAllItem

/**
 * Created by eakurnikov on 2019-12-15
 */
class ViewAllSectionAdapter {

    var itemCount: Int = 0

    fun onCreateViewHolder(parent: ViewGroup) = ViewAllSectionViewHolder.create(parent)

    fun onBindViewHolder(
            viewHolder: ViewAllSectionViewHolder,
            section: ViewAllItem.Section
    ): Unit = viewHolder.bind(section)
}