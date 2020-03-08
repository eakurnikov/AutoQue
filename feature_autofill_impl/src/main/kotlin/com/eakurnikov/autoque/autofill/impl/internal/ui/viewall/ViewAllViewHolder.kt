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
class ViewAllViewHolder(
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
        ) = ViewAllViewHolder(
            appInfoProvider,
            stubIcon,
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.faf_dataset_item, parent, false)
                .apply { setOnClickListener(onItemClickListener) }
        )
    }

    val icon: ImageView = itemView.findViewById(R.id.faf_dataset_icon)
    val login: TextView = itemView.findViewById(R.id.faf_dataset_text)
    val app: TextView = itemView.findViewById(R.id.faf_dataset_subtext)

    fun bind(fillDataDto: FillDataDto) {
        appInfoProvider.provideAppIconAsBitmap(fillDataDto.account.packageName)
            ?.let { icon.setImageBitmap(it) }
            ?: icon.setImageDrawable(stubIcon)
        login.text = fillDataDto.login.login
        app.text = fillDataDto.account.name
    }
}