package com.aurora.store.viewmodel.category

import android.app.Application
import com.aurora.gplayapi.data.models.Category
import com.aurora.store.data.RequestState

class AppCategoryViewModel(application: Application) : BaseCategoryViewModel(application) {

    init {
        type = Category.Type.APPLICATION
        requestState = RequestState.Init
        observe()
    }
}

class GameCategoryViewModel(application: Application) : BaseCategoryViewModel(application) {

    init {
        type = Category.Type.GAME
        requestState = RequestState.Init
        observe()
    }
}