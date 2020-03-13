package com.eakurnikov.autoque.autofill.impl.internal.ui.viewall

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.autoque.autofill.impl.internal.data.model.ViewAllItem

/**
 * Created by eakurnikov on 2019-12-15
 */
class ViewAllSectionViewHolder(
        itemView: View
) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup) = ViewAllSectionViewHolder(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.faf_view_all_section_item, parent, false)
        )
    }

    private val title: TextView = itemView.findViewById(R.id.faf_view_all_section_title)

    fun bind(section: ViewAllItem.Section) {
        title.text = section.title
    }
}