package com.eakurnikov.autoque.autofill.impl.internal.ui.viewall

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eakurnikov.autoque.autofill.impl.internal.data.model.ViewAllItem
import com.eakurnikov.autoque.autofill.impl.internal.domain.clientapp.AppInfoProvider

/**
 * Created by eakurnikov on 2019-12-15
 */
class ViewAllAdapter(
    appInfoProvider: AppInfoProvider,
    stubIcon: Drawable,
    onItemClickListener: View.OnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val sectionAdapter = ViewAllSectionAdapter()

    private val fillDataAdapter =
        ViewAllFillDataAdapter(appInfoProvider, stubIcon, onItemClickListener)

    var data: List<ViewAllItem> = emptyList()
        set(value) {
            sectionAdapter.itemCount = value.count { it is ViewAllItem.Section }
            fillDataAdapter.itemCount = value.size - sectionAdapter.itemCount
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ViewAllItem.Type.SECTION.id -> sectionAdapter.onCreateViewHolder(parent)
            else -> fillDataAdapter.onCreateViewHolder(parent)
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int): Unit =
        when (getItemViewType(position)) {
            ViewAllItem.Type.SECTION.id -> {
                sectionAdapter.onBindViewHolder(
                    viewHolder as ViewAllSectionViewHolder,
                    data[position] as ViewAllItem.Section
                )
            }
            else -> {
                fillDataAdapter.onBindViewHolder(
                    viewHolder as ViewAllFillDataViewHolder,
                    data[position] as ViewAllItem.FillDataHolder
                )
            }
        }

    override fun getItemViewType(position: Int): Int =
        when (data[position]) {
            is ViewAllItem.Section -> ViewAllItem.Type.SECTION.id
            else -> ViewAllItem.Type.FILL_DATA_HOLDER.id
        }

    override fun getItemCount(): Int =
        when (fillDataAdapter.itemCount) {
            0 -> 0
            else -> fillDataAdapter.itemCount + sectionAdapter.itemCount
        }
}