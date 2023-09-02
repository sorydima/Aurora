package com.aurora.store.viewmodel.all

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aurora.gplayapi.data.models.AuthData
import com.aurora.gplayapi.data.models.StreamCluster
import com.aurora.gplayapi.helpers.ClusterHelper
import com.aurora.store.data.RequestState
import com.aurora.store.data.network.HttpClient
import com.aurora.store.data.providers.AuthProvider
import com.aurora.store.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class LibraryAppsViewModel(application: Application) : BaseAndroidViewModel(application) {

    private val authData: AuthData = AuthProvider.with(application).getAuthData()
    private val clusterHelper: ClusterHelper =
        ClusterHelper(authData).using(HttpClient.getPreferredClient())

    val liveData: MutableLiveData<StreamCluster> = MutableLiveData()
    var streamCluster: StreamCluster = StreamCluster()

    init {
        observe()
    }

    override fun observe() {
        viewModelScope.launch(Dispatchers.IO) {
            supervisorScope {
                try {
                    when {
                        streamCluster.clusterAppList.isEmpty() -> {
                            val newCluster =
                                clusterHelper.getCluster(ClusterHelper.Type.MY_APPS_LIBRARY)
                            updateCluster(newCluster)
                            liveData.postValue(streamCluster)
                        }
                        streamCluster.hasNext() -> {
                            val newCluster = clusterHelper.next(streamCluster.clusterNextPageUrl)
                            updateCluster(newCluster)
                            liveData.postValue(streamCluster)
                        }
                        else -> {
                            requestState = RequestState.Complete
                        }
                    }
                } catch (e: Exception) {
                    requestState = RequestState.Pending
                }
            }
        }
    }

    private fun updateCluster(newCluster: StreamCluster) {
        streamCluster.apply {
            clusterAppList.addAll(newCluster.clusterAppList)
            clusterNextPageUrl = newCluster.clusterNextPageUrl
        }
    }
}