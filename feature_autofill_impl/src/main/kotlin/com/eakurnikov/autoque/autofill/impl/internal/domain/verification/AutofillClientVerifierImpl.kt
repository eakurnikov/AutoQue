package com.eakurnikov.autoque.autofill.impl.internal.domain.verification

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.eakurnikov.common.annotations.AppContext
import java.util.ArrayList
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 */
class AutofillClientVerifierImpl @Inject constructor(
    @AppContext private val context: Context
) : AutofillClientVerifier {

    private val knownInstallerPackageNames: MutableList<String> = ArrayList(12)

    init {
        // Google
        knownInstallerPackageNames.add("com.android.vending")
        knownInstallerPackageNames.add("com.google.android.feedback")

        // Samsung
        knownInstallerPackageNames.add("com.sec.android.app.samsungapps")
        knownInstallerPackageNames.add("com.sec.knox.containeragent")
        knownInstallerPackageNames.add("com.sec.android.easyMover")
        knownInstallerPackageNames.add("com.samsung.android.app.watchmanager")
        knownInstallerPackageNames.add("com.sec.android.preloadinstaller")

        // Xiaomi
        knownInstallerPackageNames.add("com.xiaomi.market")
        knownInstallerPackageNames.add("com.xiaomi.mipicks")

        // Meizu
        knownInstallerPackageNames.add("com.xrom.intl.appcenter")

        // Huawei
        knownInstallerPackageNames.add("com.huawei.appmarket")

        // Yandex
        knownInstallerPackageNames.add("com.yandex.store")
    }

    override fun isInstallerSafe(clientPackageName: String): Boolean {
        return isInstallerKnown(clientPackageName) || isSystemApp(clientPackageName)
    }

    override fun isForbidden(clientPackageName: String): Boolean {
        return clientPackageName == context.packageName
    }

    private fun isInstallerKnown(clientPackageName: String): Boolean {
        val installerPackageName = getInstallerPackageName(clientPackageName) ?: return false
        return knownInstallerPackageNames.contains(installerPackageName)
    }

    private fun isSystemApp(clientPackageName: String): Boolean {
        val clientAppInfo = getAppInfo(clientPackageName) ?: return false
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
        } catch (e: Throwable) {
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