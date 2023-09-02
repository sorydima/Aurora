package com.aurora.store.viewmodel.homestream

import android.app.Application
import com.aurora.gplayapi.helpers.StreamHelper
import com.aurora.store.data.ViewState

class AppsForYouViewModel(application: Application) : BaseClusterViewModel(application) {

    init {
        category = StreamHelper.Category.APPLICATION
        type = StreamHelper.Type.HOME
        liveData.postValue(ViewState.Loading)
        observe()
    }
}

class GamesForYouViewModel(application: Application) : BaseClusterViewModel(application) {

    init {
        category = StreamHelper.Category.GAME
        type = StreamHelper.Type.HOME
        liveData.postValue(ViewState.Loading)
        observe()
    }
}
