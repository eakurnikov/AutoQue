package com.eakurnikov.autoque.autofill.impl.domain.request.fill

import android.content.Context
import android.graphics.Bitmap
import android.widget.RemoteViews
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.common.annotations.AppContext
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 *
 * Produces [RemoteViews].
 */
class AutofillViewProducer
@Inject constructor(
    @AppContext private val context: Context
) {
    fun datasetAuthItemView() =
        RemoteViews(context.packageName, R.layout.faf_dataset_auth_item).apply {
            setTextViewText(R.id.faf_dataset_auth_text, context.getString(R.string.faf_dataset_auth_title))
        }

    fun datasetHeaderItemView() =
        RemoteViews(context.packageName, R.layout.faf_dataset_header_item).apply {
            setTextViewText(R.id.faf_dataset_header_text, context.getString(R.string.faf_no_exact_datasets_title))
        }

    fun datasetItemView(text: String, subtext: String, icon: Bitmap?) =
        RemoteViews(context.packageName, R.layout.faf_dataset_item).apply {
            setTextViewText(R.id.faf_dataset_text, text)
            setTextViewText(R.id.faf_dataset_subtext, subtext)

            if (icon != null) {
                setImageViewBitmap(R.id.faf_dataset_icon, icon)
            } else {
                setImageViewResource(R.id.faf_dataset_icon, R.drawable.faf_ic_lock)
            }
        }

    fun noDatasetsHeaderItemView() =
        RemoteViews(context.packageName, R.layout.faf_no_datasets_header_item).apply {
            setTextViewText(R.id.faf_no_datasets_text, context.getString(R.string.faf_no_datasets_title))
        }

    fun noDatasetsItemView() = RemoteViews(context.packageName, R.layout.faf_no_datasets_item)

    fun saveDescriptionView() =
        RemoteViews(context.packageName, R.layout.faf_save_description).apply {
            setTextViewText(R.id.faf_save_description_text, context.getString(R.string.faf_save_description_title))
        }
}