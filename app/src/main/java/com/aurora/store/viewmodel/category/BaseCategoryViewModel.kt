package com.aurora.store.viewmodel.category

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aurora.gplayapi.data.models.AuthData
import com.aurora.gplayapi.data.models.Category
import com.aurora.gplayapi.helpers.CategoryHelper
import com.aurora.store.data.RequestState
import com.aurora.store.data.network.HttpClient
import com.aurora.store.data.providers.AuthProvider
import com.aurora.store.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseCategoryViewModel(application: Application) : BaseAndroidViewModel(application) {

    private val authData: AuthData = AuthProvider
        .with(application)
        .getAuthData()

    private val streamHelper: CategoryHelper = CategoryHelper(authData)
        .using(HttpClient.getPreferredClient())

    val liveData: MutableLiveData<List<Category>> = MutableLiveData()

    lateinit var type: Category.Type

    override fun observe() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                liveData.postValue(streamHelper.getAllCategoriesList(type))
                requestState = RequestState.Complete
            } catch (e: Exception) {
                requestState = RequestState.Pending
            }
        }
    }
}