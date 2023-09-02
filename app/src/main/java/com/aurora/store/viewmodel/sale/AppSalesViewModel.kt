package com.aurora.store.viewmodel.sale

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aurora.gplayapi.data.models.App
import com.aurora.gplayapi.data.models.AuthData
import com.aurora.gplayapi.helpers.AppSalesHelper
import com.aurora.store.data.network.HttpClient
import com.aurora.store.data.providers.AuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppSalesViewModel(application: Application) : AndroidViewModel(application) {

    private val authData: AuthData = AuthProvider.with(application).getAuthData()
    private val appSalesHelper: AppSalesHelper =
        AppSalesHelper(authData).using(HttpClient.getPreferredClient())

    val liveAppList: MutableLiveData<List<App>> = MutableLiveData()

    private var page: Int = 0
    private val appList: MutableList<App> = mutableListOf()

    init {
        observeAppSales()
    }

    private fun observeAppSales() {
        appList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            appList.addAll(getSearchResults())
            liveAppList.postValue(appList)
        }
    }

    private fun getSearchResults(
    ): List<App> {
        return appSalesHelper.getAppsOnSale(page = page++, offer = 100)
    }

    fun next() {
        viewModelScope.launch(Dispatchers.IO) {
            val newAppList = getSearchResults()
            if (newAppList.isNotEmpty()) {
                appList.addAll(getSearchResults())
                liveAppList.postValue(appList)
            }
        }
    }
}