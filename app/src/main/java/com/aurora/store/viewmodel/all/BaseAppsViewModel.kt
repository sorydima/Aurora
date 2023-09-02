package com.aurora.store.viewmodel.all

import android.app.Application
import android.content.pm.PackageInfo
import androidx.lifecycle.MutableLiveData
import com.aurora.gplayapi.data.models.App
import com.aurora.gplayapi.helpers.AppDetailsHelper
import com.aurora.store.data.network.HttpClient
import com.aurora.store.data.providers.AuthProvider
import com.aurora.store.data.providers.BlacklistProvider
import com.aurora.store.util.PackageUtil
import com.aurora.store.util.Preferences
import com.aurora.store.viewmodel.BaseAndroidViewModel

abstract class BaseAppsViewModel(application: Application) : BaseAndroidViewModel(application) {

    private val authData = AuthProvider
        .with(application)
        .getAuthData()

    private val appDetailsHelper = AppDetailsHelper(authData)
        .using(HttpClient.getPreferredClient())

    var blacklistProvider = BlacklistProvider
        .with(application)

    val liveData: MutableLiveData<List<App>> = MutableLiveData()

    var appList: MutableList<App> = mutableListOf()
    var packageInfoMap: MutableMap<String, PackageInfo> = mutableMapOf()

    fun getFilteredApps(): List<App> {
        val blackList = blacklistProvider.getBlackList()
        val gapps = getGApps()

        val isGoogleFilterEnabled = Preferences.getBoolean(
            getApplication(),
            Preferences.PREFERENCE_FILTER_GOOGLE
        )

        packageInfoMap.clear()
        packageInfoMap = PackageUtil.getPackageInfoMap(getApplication())

        packageInfoMap.keys.let { packages ->
            /*Filter black list*/
            var filtersPackages = packages
                .filter { !blackList.contains(it) }

            /*Filter google apps*/
            if (isGoogleFilterEnabled)
                filtersPackages = filtersPackages
                    .filter { !it.startsWith("com.google") }
                    .filter { !gapps.contains(it) }

            return appDetailsHelper
                .getAppByPackageName(filtersPackages)
                .filter { it.displayName.isNotEmpty() }
        }
    }

    open fun getGApps(): Set<String> {
        val gapps: MutableSet<String> = HashSet()
        gapps.add("com.chrome.beta")
        gapps.add("com.chrome.canary")
        gapps.add("com.chrome.dev")
        gapps.add("com.android.chrome")
        gapps.add("com.niksoftware.snapseed")
        gapps.add("com.google.toontastic")
        return gapps
    }
}