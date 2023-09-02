package com.aurora.store.viewmodel.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aurora.gplayapi.SearchSuggestEntry
import com.aurora.gplayapi.data.models.AuthData
import com.aurora.gplayapi.helpers.SearchHelper
import com.aurora.store.data.network.HttpClient
import com.aurora.store.data.providers.AuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchSuggestionViewModel(application: Application) : AndroidViewModel(application) {

    private val authData: AuthData = AuthProvider
        .with(application)
        .getAuthData()

    private val searchHelper: SearchHelper = SearchHelper(authData)
        .using(HttpClient.getPreferredClient())

    val liveSearchSuggestions: MutableLiveData<List<SearchSuggestEntry>> = MutableLiveData()

    fun observeStreamBundles(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            liveSearchSuggestions.postValue(getSearchSuggestions(query))
        }
    }

    private fun getSearchSuggestions(
        query: String
    ): List<SearchSuggestEntry> {
        return searchHelper.searchSuggestions(query)
    }
}