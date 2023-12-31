package com.aurora.store.view.epoxy.views.details

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import coil.load
import coil.transform.RoundedCornersTransformation
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.aurora.extensions.px
import com.aurora.gplayapi.data.models.Artwork
import com.aurora.store.R
import com.aurora.store.databinding.ViewScreenshotMiniBinding
import com.aurora.store.view.epoxy.views.BaseView

@ModelView(
    autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class MiniScreenshotView : RelativeLayout {

    private lateinit var B: ViewScreenshotMiniBinding

    private var position: Int = 0

    interface ScreenshotCallback {
        fun onClick(position: Int = 0)
    }

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
        val view = inflate(context, R.layout.view_screenshot_mini, this)
        B = ViewScreenshotMiniBinding.bind(view)
    }

    @ModelProp
    fun position(pos: Int) {
        position = pos
    }

    @ModelProp
    fun artwork(artwork: Artwork) {
        normalizeSize(artwork)
        B.img.load("${artwork.url}=rw-w480-v1-e15") {
            placeholder(R.drawable.bg_rounded)
            transformations(RoundedCornersTransformation(8.px.toFloat()))
        }
    }

    private fun normalizeSize(artwork: Artwork) {
        if (artwork.height != 0 && artwork.width != 0) {

            val artworkHeight = artwork.height
            val artworkWidth = artwork.width

            val normalizedHeight: Float
            val normalizedWidth: Float

            when {
                artworkHeight == artworkWidth -> {
                    normalizedHeight = 120f
                    normalizedWidth = 120f
                }
                else -> {
                    val factor = artworkHeight / 120f
                    normalizedHeight = 120f
                    normalizedWidth = (artworkWidth / factor)
                }
            }

            B.img.layoutParams.height = normalizedHeight.px.toInt()
            B.img.layoutParams.width = normalizedWidth.px.toInt()
            B.img.requestLayout()
        }
    }

    @CallbackProp
    fun callback(screenshotCallback: ScreenshotCallback?) {
        B.img.setOnClickListener {
            screenshotCallback?.onClick(position)
        }
    }
}
