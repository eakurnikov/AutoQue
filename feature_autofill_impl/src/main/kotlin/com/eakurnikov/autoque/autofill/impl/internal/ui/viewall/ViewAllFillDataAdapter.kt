package com.eakurnikov.autoque.autofill.impl.internal.ui.viewall

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import com.eakurnikov.autoque.autofill.impl.internal.data.model.ViewAllItem
import com.eakurnikov.autoque.autofill.impl.internal.domain.clientapp.AppInfoProvider

/**
 * Created by eakurnikov on 2019-12-15
 */
class ViewAllFillDataAdapter(
    private val appInfoProvider: AppInfoProvider,
    private val stubIcon: Drawable,
    private val onItemClickListener: View.OnClickListener
) {
    var itemCount: Int = 0

    fun onCreateViewHolder(parent: ViewGroup) = ViewAllFillDataViewHolder.create(
        appInfoProvider,
        stubIcon,
        onItemClickListener,
        parent
    )

    fun onBindViewHolder(
        viewHolder: ViewAllFillDataViewHolder,
        dtoHolder: ViewAllItem.FillDataHolder
    ): Unit = viewHolder.bind(dtoHolder.fillDataDto)
}