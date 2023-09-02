package com.aurora.store.viewmodel.topchart

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aurora.gplayapi.data.models.AuthData
import com.aurora.gplayapi.data.models.StreamCluster
import com.aurora.gplayapi.helpers.TopChartsHelper
import com.aurora.store.data.RequestState
import com.aurora.store.data.network.HttpClient
import com.aurora.store.data.providers.AuthProvider
import com.aurora.store.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

abstract class BaseChartViewModel(application: Application) : BaseAndroidViewModel(application) {

    private val authData: AuthData = AuthProvider.with(application).getAuthData()
    private val topChartsHelper: TopChartsHelper =
        TopChartsHelper(authData).using(HttpClient.getPreferredClient())

    lateinit var type: TopChartsHelper.Type
    lateinit var chart: TopChartsHelper.Chart

    val liveData: MutableLiveData<StreamCluster> = MutableLiveData()
    var streamCluster: StreamCluster = StreamCluster()

    fun getStreamCluster(
        type: TopChartsHelper.Type,
        chart: TopChartsHelper.Chart
    ): StreamCluster {
        return topChartsHelper.getCluster(type, chart)
    }

    override fun observe() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                streamCluster = getStreamCluster(type, chart)
                liveData.postValue(streamCluster)
            } catch (e: Exception) {
                requestState = RequestState.Pending
            }
        }
    }

    fun nextCluster() {
        viewModelScope.launch(Dispatchers.IO) {
            supervisorScope {
                try {
                    if (streamCluster.hasNext()) {
                        val newCluster = topChartsHelper.getNextStreamCluster(
                            streamCluster.clusterNextPageUrl
                        )

                        streamCluster.apply {
                            clusterAppList.addAll(newCluster.clusterAppList)
                            clusterNextPageUrl = newCluster.clusterNextPageUrl
                        }

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
}
