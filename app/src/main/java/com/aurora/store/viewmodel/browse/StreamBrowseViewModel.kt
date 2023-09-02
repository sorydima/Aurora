package com.aurora.store.viewmodel.browse

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aurora.gplayapi.data.models.AuthData
import com.aurora.gplayapi.data.models.StreamCluster
import com.aurora.gplayapi.helpers.StreamHelper
import com.aurora.store.data.RequestState
import com.aurora.store.data.network.HttpClient
import com.aurora.store.data.providers.AuthProvider
import com.aurora.store.util.Log
import com.aurora.store.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class StreamBrowseViewModel(application: Application) : BaseAndroidViewModel(application) {

    private val authData: AuthData = AuthProvider.with(application).getAuthData()
    private val streamHelper: StreamHelper = StreamHelper(authData)
        .using(HttpClient.getPreferredClient())

    val liveData: MutableLiveData<StreamCluster> = MutableLiveData()
    var streamCluster: StreamCluster = StreamCluster()

    fun getStreamBundle(browseUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            liveData.postValue(getInitialCluster(browseUrl))
        }
    }

    private fun getInitialCluster(
        browseUrl: String
    ): StreamCluster {

        requestState = RequestState.Init

        val browseResponse = streamHelper.getBrowseStreamResponse(browseUrl)

        if (browseResponse.contentsUrl.isNotEmpty())
            streamCluster = streamHelper.getNextStreamCluster(browseResponse.contentsUrl)
        else if (browseResponse.hasBrowseTab())
            streamCluster = streamHelper.getNextStreamCluster(browseResponse.browseTab.listUrl)

        streamCluster.apply {
            clusterTitle = browseResponse.title
        }

        return streamCluster
    }

    fun nextCluster() {
        viewModelScope.launch(Dispatchers.IO) {
            supervisorScope {
                try {
                    if (streamCluster.hasNext()) {
                        val newCluster = streamHelper.getNextStreamCluster(
                            streamCluster.clusterNextPageUrl
                        )

                        streamCluster.apply {
                            clusterAppList.addAll(newCluster.clusterAppList)
                            clusterNextPageUrl = newCluster.clusterNextPageUrl
                        }

                        liveData.postValue(streamCluster)
                    } else {
                        Log.i("End of Bundle")
                        requestState = RequestState.Complete
                    }
                } catch (e: Exception) {
                    requestState = RequestState.Pending
                }
            }
        }
    }

    override fun observe() {
        requestState = RequestState.Init
    }
}
