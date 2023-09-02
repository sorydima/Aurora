package com.aurora.store.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.aurora.store.data.RequestState
import com.aurora.store.data.network.HttpClient
import com.aurora.store.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.launchIn
import java.lang.reflect.Modifier

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    val responseCode = HttpClient.getPreferredClient().responseCode

    protected val gson: Gson = GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
        .create()

    protected var requestState: RequestState

    init {
        Log.i("${javaClass.simpleName} Created")

        requestState = RequestState.Init

        // Start collecting response code for requests
        responseCode.launchIn(viewModelScope)
    }

    abstract fun observe()

    private fun redoLastNetworkTask() {
        when (requestState) {
            RequestState.Pending -> {
                observe()
            }
            else -> {

            }
        }
    }
}
