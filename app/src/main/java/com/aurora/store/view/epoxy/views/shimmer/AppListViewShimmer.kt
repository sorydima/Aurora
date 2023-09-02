package com.aurora.store.view.epoxy.views.shimmer

import android.content.Context
import android.util.AttributeSet
import com.airbnb.epoxy.ModelView
import com.aurora.store.R
import com.aurora.store.databinding.ViewAppListShimmerBinding
import com.aurora.store.view.epoxy.views.BaseView
import com.facebook.shimmer.ShimmerFrameLayout

@ModelView(
    autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class AppListViewShimmer : ShimmerFrameLayout {

    private lateinit var B: ViewAppListShimmerBinding

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context?) {
        val view = inflate(context, R.layout.view_app_list_shimmer, this)
        B = ViewAppListShimmerBinding.bind(view)
    }
}
