package com.aurora.store.viewmodel.editorschoice

import android.app.Application
import com.aurora.gplayapi.helpers.StreamHelper

class AppEditorChoiceViewModel(application: Application) : BaseEditorChoiceViewModel(application) {
    init {
        category = StreamHelper.Category.APPLICATION
        observe()
    }
}


class GameEditorChoiceViewModel(application: Application) : BaseEditorChoiceViewModel(application) {
    init {
        category = StreamHelper.Category.GAME
        observe()
    }
}
