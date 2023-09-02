package com.aurora.store.view.epoxy.views.shimmer

import android.content.Context
import android.util.AttributeSet
import com.airbnb.epoxy.ModelView
import com.aurora.store.R
import com.aurora.store.databinding.ViewAppShimmerBinding
import com.aurora.store.view.epoxy.views.BaseView
import com.facebook.shimmer.ShimmerFrameLayout

@ModelView(
    autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class AppViewShimmer : ShimmerFrameLayout {

    private lateinit var B: ViewAppShimmerBinding

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
        val view = inflate(context, R.layout.view_app_shimmer, this)
        B = ViewAppShimmerBinding.bind(view)
    }
}
