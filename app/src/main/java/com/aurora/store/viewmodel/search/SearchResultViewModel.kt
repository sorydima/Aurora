package com.aurora.store.viewmodel.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aurora.extensions.flushAndAdd
import com.aurora.gplayapi.data.models.AuthData
import com.aurora.gplayapi.data.models.SearchBundle
import com.aurora.gplayapi.helpers.SearchHelper
import com.aurora.store.data.RequestState
import com.aurora.store.data.network.HttpClient
import com.aurora.store.data.providers.AuthProvider
import com.aurora.store.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class SearchResultViewModel(application: Application) : BaseAndroidViewModel(application) {

    private val TAG = SearchResultViewModel::class.java.simpleName
    private val authData: AuthData = AuthProvider
        .with(application)
        .getAuthData()

    private val searchHelper: SearchHelper = SearchHelper(authData)
        .using(HttpClient.getPreferredClient())

    val liveData: MutableLiveData<SearchBundle> = MutableLiveData()

    private var searchBundle: SearchBundle = SearchBundle()

    fun observeSearchResults(query: String) {
        //Clear old results
        searchBundle.subBundles.clear()
        searchBundle.appList.clear()
        //Fetch new results
        viewModelScope.launch(Dispatchers.IO) {
            supervisorScope {
                try {
                    searchBundle = search(query)
                    liveData.postValue(searchBundle)
                } catch (e: Exception) {

                }
            }
        }
    }

    private fun search(
        query: String
    ): SearchBundle {
        return searchHelper.searchResults(query)
    }

    @Synchronized
    fun next(nextSubBundleSet: MutableSet<SearchBundle.SubBundle>) {
        viewModelScope.launch(Dispatchers.IO) {
            supervisorScope {
                try {
                    if (nextSubBundleSet.isNotEmpty() && responseCode.value != 429) {
                        val newSearchBundle = searchHelper.next(nextSubBundleSet)
                        if (newSearchBundle.appList.isNotEmpty()) {
                            searchBundle.apply {
                                subBundles.flushAndAdd(newSearchBundle.subBundles)
                                appList.addAll(newSearchBundle.appList)
                            }

                            liveData.postValue(searchBundle)
                        }
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "Response code: ${responseCode.value}", e)
                }
            }
        }
    }

    override fun observe() {
        requestState = RequestState.Init
    }
}
