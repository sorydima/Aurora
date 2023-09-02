package com.aurora.store.viewmodel.editorschoice

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aurora.gplayapi.data.models.AuthData
import com.aurora.gplayapi.data.models.editor.EditorChoiceBundle
import com.aurora.gplayapi.helpers.StreamHelper
import com.aurora.store.data.RequestState
import com.aurora.store.data.network.HttpClient
import com.aurora.store.data.providers.AuthProvider
import com.aurora.store.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

open class BaseEditorChoiceViewModel(application: Application) : BaseAndroidViewModel(application) {

    private val authData: AuthData = AuthProvider
        .with(application)
        .getAuthData()

    private val streamHelper: StreamHelper = StreamHelper(authData)
        .using(HttpClient.getPreferredClient())

    lateinit var category: StreamHelper.Category

    val liveData: MutableLiveData<List<EditorChoiceBundle>> = MutableLiveData()

    override fun observe() {
        viewModelScope.launch(Dispatchers.IO) {
            supervisorScope {
                try {
                    val editorChoiceBundle = streamHelper.getEditorChoiceStream(category)
                    liveData.postValue(editorChoiceBundle)
                    requestState = RequestState.Complete
                } catch (e: Exception) {
                    requestState = RequestState.Pending
                }
            }
        }
    }
}
