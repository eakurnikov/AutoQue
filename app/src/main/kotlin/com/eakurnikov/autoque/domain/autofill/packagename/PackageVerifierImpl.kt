package com.eakurnikov.autoque.domain.autofill.packagename

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.eakurnikov.autoque.autofill.api.dependencies.domain.packagename.PackageVerifier
import com.eakurnikov.common.annotations.AppContext
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 */
class PackageVerifierImpl @Inject constructor(
    @AppContext private val context: Context
) : PackageVerifier {

    private val allowedInstallerPackageNames = listOf(
        "com.android.vending",
        "com.google.android.feedback",

        "com.sec.android.app.samsungapps",
        "com.sec.knox.containeragent",
        "com.sec.android.easyMover",
        "com.samsung.android.app.watchmanager",
        "com.sec.android.preloadinstaller",

        "com.xiaomi.market",
        "com.xiaomi.mipicks",

        "com.huawei.appmarket",

        "com.yandex.store"
    )

    override fun verifyPackage(clientPackageName: String): Boolean {
        return false // isInstallerKnown(clientPackageName) || isSystemApp(clientPackageName)
    }

    private fun isInstallerKnown(clientPackageName: String): Boolean {
        val installerPackageName: String =
            getInstallerPackageName(clientPackageName) ?: return false
        return allowedInstallerPackageNames.contains(installerPackageName)
    }

    private fun isSystemApp(clientPackageName: String): Boolean {
        val clientAppInfo: ApplicationInfo = getAppInfo(clientPackageName) ?: return false
        return hasSystemAppFlag(clientAppInfo) &&
                (hasSystemSignature(clientAppInfo) || !hasLaunchIntent(clientPackageName))
    }

    private fun getInstallerPackageName(clientPackageName: String): String? {
        return try {
            context.packageManager.getInstallerPackageName(clientPackageName)
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    private fun getAppInfo(clientPackageName: String): ApplicationInfo? {
        return try {
            context.packageManager.getApplicationInfo(clientPackageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }

    private fun hasLaunchIntent(clientPackageName: String): Boolean {
        return try {
            context.packageManager.getLaunchIntentForPackage(clientPackageName) != null
        } catch (error: Throwable) {
            false
        }
    }

    private fun hasSystemSignature(appInfo: ApplicationInfo): Boolean {
        return try {
            with(context.packageManager) {
                val systemInfo: PackageInfo =
                    getPackageInfo("android", PackageManager.GET_SIGNATURES) ?: return@with false

                val clientAppInfo: PackageInfo =
                    getPackageInfo(appInfo.packageName, PackageManager.GET_SIGNATURES)
                        ?: return@with false

                systemInfo.signatures[0] == clientAppInfo.signatures[0]
            }
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun hasSystemAppFlag(appInfo: ApplicationInfo): Boolean {
        return (appInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0) ||
                (appInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0)
    }
}