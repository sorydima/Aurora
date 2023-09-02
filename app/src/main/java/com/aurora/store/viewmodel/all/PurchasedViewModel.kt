package com.aurora.store.viewmodel.all

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aurora.gplayapi.data.models.App
import com.aurora.gplayapi.helpers.PurchaseHelper
import com.aurora.store.data.RequestState
import com.aurora.store.data.network.HttpClient
import com.aurora.store.data.providers.AuthProvider
import com.aurora.store.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class PurchasedViewModel(application: Application) : BaseAndroidViewModel(application) {

    private val authData = AuthProvider
        .with(application)
        .getAuthData()

    private val purchaseHelper = PurchaseHelper(authData)
        .using(HttpClient.getPreferredClient())

    val liveData: MutableLiveData<List<App>> = MutableLiveData()

    var appList: MutableList<App> = mutableListOf()

    init {
        requestState = RequestState.Init
        observe()
    }

    override fun observe() {
        viewModelScope.launch(Dispatchers.IO) {
            supervisorScope {
                try {
                    appList.addAll(purchaseHelper.getPurchaseHistory(appList.size))
                    liveData.postValue(appList)
                    requestState = RequestState.Complete
                } catch (e: Exception) {
                    requestState = RequestState.Pending
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}