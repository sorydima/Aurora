package com.aurora.store.viewmodel.details

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aurora.gplayapi.data.models.AuthData
import com.aurora.gplayapi.data.models.StreamBundle
import com.aurora.gplayapi.data.models.StreamCluster
import com.aurora.gplayapi.data.models.details.DevStream
import com.aurora.gplayapi.helpers.AppDetailsHelper
import com.aurora.gplayapi.helpers.StreamHelper
import com.aurora.store.data.RequestState
import com.aurora.store.data.ViewState
import com.aurora.store.data.network.HttpClient
import com.aurora.store.data.providers.AuthProvider
import com.aurora.store.util.Log
import com.aurora.store.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class DevProfileViewModel(application: Application) : BaseAndroidViewModel(application) {

    private var authData: AuthData = AuthProvider.with(application).getAuthData()
    private var appDetailsHelper = AppDetailsHelper(authData).using(HttpClient.getPreferredClient())
    private var streamHelper = StreamHelper(authData)

    val liveData: MutableLiveData<ViewState> = MutableLiveData()
    var devStream:DevStream = DevStream()
    var streamBundle: StreamBundle = StreamBundle()

    lateinit var type: StreamHelper.Type
    lateinit var category: StreamHelper.Category

    override fun observe() {

    }

    fun getStreamBundle(
        devId: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            supervisorScope {
                try {
                    devStream = appDetailsHelper.getDeveloperStream(devId)
                    streamBundle = devStream.streamBundle
                    liveData.postValue(ViewState.Success(devStream))
                    requestState = RequestState.Complete
                } catch (e: Exception) {
                    requestState = RequestState.Pending
                    liveData.postValue(ViewState.Error(e.message))
                }
            }
        }
    }

    fun observeCluster(streamCluster: StreamCluster) {
        viewModelScope.launch(Dispatchers.IO) {
            supervisorScope {
                try {
                    if (streamCluster.hasNext()) {
                        val newCluster = streamHelper.getNextStreamCluster(streamCluster.clusterNextPageUrl)
                        updateCluster(newCluster)
                        devStream.streamBundle = streamBundle
                        liveData.postValue(ViewState.Success(devStream))
                    } else {
                        Log.i("End of cluster")
                        streamCluster.clusterNextPageUrl = String()
                    }
                } catch (e: Exception) {
                    liveData.postValue(ViewState.Error(e.message))
                }
            }
        }
    }

    private fun updateCluster(newCluster: StreamCluster) {
        streamBundle.streamClusters[newCluster.id]?.apply {
            clusterAppList.addAll(newCluster.clusterAppList)
            clusterNextPageUrl = newCluster.clusterNextPageUrl
        }
    }
}
