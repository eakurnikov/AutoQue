package com.eakurnikov.autoque.autofill.impl.internal.ui

import android.content.Context
import android.graphics.Bitmap
import android.widget.RemoteViews
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.common.annotations.AppContext
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-16
 */
class AutofillViewBuilder @Inject constructor(
    @AppContext private val context: Context
) {
    fun buildSaveDescriptionView(): RemoteViews =
        RemoteViews(context.packageName, R.layout.faf_save_description)

    fun buildDatasetAuthItemView(): RemoteViews =
        RemoteViews(context.packageName, R.layout.faf_dataset_auth_item)

    fun buildDatasetHeaderView(): RemoteViews =
        RemoteViews(context.packageName, R.layout.faf_dataset_header_item)

    fun buildDatasetUnsafeItemView(): RemoteViews =
        RemoteViews(context.packageName, R.layout.faf_dataset_item)

    fun buildDatasetItemView(text: String, subtext: String, icon: Bitmap?): RemoteViews =
        RemoteViews(context.packageName, R.layout.faf_dataset_item).apply {
            setTextViewText(R.id.faf_dataset_text, text)
            setTextViewText(R.id.faf_dataset_subtext, subtext)

            if (icon != null) {
                setImageViewBitmap(R.id.faf_dataset_icon, icon)
            } else {
                setImageViewResource(R.id.faf_dataset_icon, R.drawable.faf_ic_person)
            }
        }

    fun buildAutoQueToast(text: String): RemoteViews =
        RemoteViews(context.packageName, R.layout.faf_no_datasets_toast).apply {
            setTextViewText(R.id.faf_toast_text, text)
        }
}