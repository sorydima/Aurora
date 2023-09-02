package com.aurora.store.viewmodel.homestream

import android.app.Application
import com.aurora.gplayapi.helpers.StreamHelper
import com.aurora.store.data.ViewState

class EarlyAccessAppsViewModel(application: Application) : BaseClusterViewModel(application) {

    init {
        category = StreamHelper.Category.APPLICATION
        type = StreamHelper.Type.EARLY_ACCESS
        liveData.postValue(ViewState.Loading)
        observe()
    }
}

class EarlyAccessGamesViewModel(application: Application) :
    BaseClusterViewModel(application) {

    init {
        category = StreamHelper.Category.GAME
        type = StreamHelper.Type.EARLY_ACCESS
        liveData.postValue(ViewState.Loading)
        observe()
    }
}