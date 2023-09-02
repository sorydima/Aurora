package com.aurora.store.viewmodel.browse

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aurora.gplayapi.data.models.AuthData
import com.aurora.gplayapi.data.models.StreamCluster
import com.aurora.gplayapi.helpers.ExpandedBrowseHelper
import com.aurora.store.data.RequestState
import com.aurora.store.data.network.HttpClient
import com.aurora.store.data.providers.AuthProvider
import com.aurora.store.util.Log
import com.aurora.store.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class ExpandedStreamBrowseViewModel(application: Application) : BaseAndroidViewModel(application) {

    private val authData: AuthData = AuthProvider.with(application).getAuthData()
    private val streamHelper: ExpandedBrowseHelper = ExpandedBrowseHelper(authData)
        .using(HttpClient.getPreferredClient())

    val liveData: MutableLiveData<StreamCluster> = MutableLiveData()
    var streamCluster: StreamCluster = StreamCluster()

    override fun observe() {
        requestState = RequestState.Init
    }

    fun getInitialCluster(expandedStreamUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            supervisorScope {
                try {
                    val browseResponse = streamHelper.getBrowseStreamResponse(expandedStreamUrl)
                    if (browseResponse.hasBrowseTab()) {
                        streamCluster =
                            streamHelper.getExpandedBrowseClusters(browseResponse.browseTab.listUrl)
                        liveData.postValue(streamCluster)
                    } else {
                        requestState = RequestState.Complete
                    }
                } catch (e: Exception) {
                    requestState = RequestState.Pending
                }
            }
        }
    }

    fun next() {
        Log.e("NEXT CALED")
        viewModelScope.launch(Dispatchers.IO) {
            supervisorScope {
                try {
                    val newCluster = streamHelper.getExpandedBrowseClusters(
                        streamCluster.clusterNextPageUrl
                    )

                    streamCluster.apply {
                        clusterAppList.addAll(newCluster.clusterAppList)
                        clusterNextPageUrl = newCluster.clusterNextPageUrl
                    }

                    liveData.postValue(streamCluster)

                    if (!streamCluster.hasNext()) {
                        Log.i("End of Bundle")
                        requestState = RequestState.Complete
                    }
                } catch (e: Exception) {
                    requestState = RequestState.Pending
                }
            }
        }
    }
}
