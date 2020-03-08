package com.eakurnikov.autoque.autofill.impl.internal.ui.viewall

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto
import com.eakurnikov.autoque.autofill.impl.internal.domain.clientapp.AppInfoProvider

/**
 * Created by eakurnikov on 2019-12-15
 */
class ViewAllAdapter(
    private val appInfoProvider: AppInfoProvider,
    private val stubIcon: Drawable,
    private val onItemClickListener: View.OnClickListener
) : RecyclerView.Adapter<ViewAllViewHolder>() {

    var data: List<FillDataDto> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAllViewHolder =
        ViewAllViewHolder.create(appInfoProvider, stubIcon, onItemClickListener, parent)

    override fun onBindViewHolder(viewHolder: ViewAllViewHolder, position: Int): Unit =
        viewHolder.bind(data[position])

    override fun getItemCount(): Int = data.size
}