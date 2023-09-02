package com.aurora.store.view.epoxy.views.details

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.widget.RelativeLayout
import coil.load
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.aurora.gplayapi.data.models.Artwork
import com.aurora.store.R
import com.aurora.store.databinding.ViewScreenshotLargeBinding
import com.aurora.store.view.epoxy.views.BaseView

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_MATCH_HEIGHT,
    baseModelClass = BaseView::class
)
class LargeScreenshotView : RelativeLayout {

    private lateinit var B: ViewScreenshotLargeBinding

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
        val view = inflate(context, R.layout.view_screenshot_large, this)
        B = ViewScreenshotLargeBinding.bind(view)
    }

    @ModelProp
    fun artwork(artwork: Artwork) {
        val displayMetrics = Resources.getSystem().displayMetrics
        B.img.load("${artwork.url}=rw-w${displayMetrics.widthPixels}-v1-e15") {
            placeholder(R.drawable.bg_placeholder)
        }
    }
}
