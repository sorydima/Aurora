package com.aurora.store.viewmodel.editorschoice

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aurora.gplayapi.data.models.App
import com.aurora.gplayapi.data.models.AuthData
import com.aurora.gplayapi.helpers.StreamHelper
import com.aurora.store.data.RequestState
import com.aurora.store.data.network.HttpClient
import com.aurora.store.data.providers.AuthProvider
import com.aurora.store.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class EditorBrowseViewModel(application: Application) : BaseAndroidViewModel(application) {

    private val authData: AuthData = AuthProvider.with(application).getAuthData()
    private val streamHelper: StreamHelper = StreamHelper(authData)
        .using(HttpClient.getPreferredClient())

    val liveData: MutableLiveData<MutableList<App>> = MutableLiveData()
    val appList: MutableList<App> = mutableListOf()

    override fun observe() {
        requestState = RequestState.Init
    }

    fun getEditorStreamBundle(
        browseUrl: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            supervisorScope {
                try {

                    requestState = RequestState.Init

                    val browseResponse = streamHelper.getBrowseStreamResponse(browseUrl)
                    val listResponse =
                        streamHelper.getNextStreamResponse(browseResponse.browseTab.listUrl)

                    listResponse.itemList.forEach {
                        it?.let {
                            it.subItemList.forEach {
                                appList.addAll(streamHelper.getAppsFromItem(it))
                            }
                        }
                    }

                    liveData.postValue(appList)
                    requestState = RequestState.Complete

                } catch (e: Exception) {
                    requestState = RequestState.Pending
                }
            }
        }
    }
}
