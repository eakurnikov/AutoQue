package com.eakurnikov.autoque.autofill.impl.internal.domain.clientapp

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.common.annotations.AppContext
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-09
 *
 * Provides client app data, such as it's icon and name, used for filling and saving data.
 */
class AppInfoProvider @Inject constructor(
    @AppContext private val context: Context
) {
    fun provideAppIconAsDrawable(packageName: String): Drawable? {
        return when {
            packageName.isEmpty() -> null
            else -> try {
                context.packageManager.getApplicationIcon(packageName)
            } catch (e: PackageManager.NameNotFoundException) {
                null
            }
        }
    }

    fun provideAppIconAsBitmap(packageName: String): Bitmap? {
        val appIconDrawable: Drawable =
            provideAppIconAsDrawable(packageName)
                ?.apply { setBounds(0, 0, minimumWidth, minimumHeight) }
                ?: return null

        val appIconBitmap: Bitmap = Bitmap.createBitmap(
            appIconDrawable.minimumWidth,
            appIconDrawable.minimumHeight,
            Bitmap.Config.ARGB_8888
        )

        appIconDrawable.draw(Canvas(appIconBitmap))

        return appIconBitmap
    }

    fun provideAppName(packageName: String): String {
        if (packageName.isEmpty()) {
            return context.getString(R.string.faf_default_client_app_name)
        }

        return with(context.packageManager) {
            try {
                getApplicationInfo(packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                null
            }?.let {
                getApplicationLabel(it) as String
            }
        } ?: packageName.substringAfterLast('.')
    }
}