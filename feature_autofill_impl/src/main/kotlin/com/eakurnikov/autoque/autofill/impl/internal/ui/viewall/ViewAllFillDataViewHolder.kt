package com.eakurnikov.autoque.autofill.impl.internal.ui.viewall

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto
import com.eakurnikov.autoque.autofill.impl.internal.domain.clientapp.AppInfoProvider

/**
 * Created by eakurnikov on 2019-12-15
 */
class ViewAllFillDataViewHolder(
    private val appInfoProvider: AppInfoProvider,
    private val stubIcon: Drawable,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(
                appInfoProvider: AppInfoProvider,
                stubIcon: Drawable,
                onItemClickListener: View.OnClickListener,
                parent: ViewGroup
        ) = ViewAllFillDataViewHolder(
                appInfoProvider,
                stubIcon,
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.faf_dataset_item, parent, false)
                        .apply { setOnClickListener(onItemClickListener) }
        )
    }

    private val icon: ImageView = itemView.findViewById(R.id.faf_dataset_icon)
    private val login: TextView = itemView.findViewById(R.id.faf_dataset_text)
    private val appName: TextView = itemView.findViewById(R.id.faf_dataset_subtext)

    fun bind(fillDataDto: FillDataDto) {
        appInfoProvider.provideAppIconAsBitmap(fillDataDto.account.packageName)
            ?.let { icon.setImageBitmap(it) }
            ?: icon.setImageDrawable(stubIcon)
        login.text = fillDataDto.login.login
        appName.text = fillDataDto.account.name
    }
}