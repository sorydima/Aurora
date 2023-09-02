package com.aurora.store.view.epoxy.views

import android.view.View
import android.view.animation.AnimationUtils
import com.airbnb.epoxy.EpoxyModel
import com.aurora.store.view.epoxy.views.app.AppListView

abstract class BaseView<T : View> : EpoxyModel<T>() {

    override fun bind(view: T) {
        super.bind(view)
        when (view) {
            is AppListView -> {
                view.startAnimation(
                    AnimationUtils.loadAnimation(
                        view.context,
                        android.R.anim.fade_in
                    )
                )
            }
        }
    }

    override fun unbind(view: T) {
        when (view) {
            is AppListView -> {
                view.clearAnimation()
            }
        }
    }
}
