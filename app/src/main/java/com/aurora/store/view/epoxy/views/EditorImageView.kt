package com.aurora.store.view.epoxy.views

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
import com.aurora.store.databinding.ViewEditorImageBinding

@ModelView(
    autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class EditorImageView : RelativeLayout {

    private lateinit var B: ViewEditorImageBinding

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
        val view = inflate(context, R.layout.view_editor_image, this)
        B = ViewEditorImageBinding.bind(view)
    }

    @ModelProp
    fun artwork(artwork: Artwork) {
        when (artwork.type) {
            14 -> {
                B.img.layoutParams.height = 108.px.toInt()
                B.img.layoutParams.width = 192.px.toInt()
                B.img.requestLayout()

                B.img.load(artwork.url) {
                    transformations(RoundedCornersTransformation(8.px.toFloat()))
                }
            }
            else -> {
                B.img.layoutParams.width = 24.px.toInt()
                B.img.layoutParams.height = 24.px.toInt()
                B.img.requestLayout()
                B.img.load(artwork.url) {
                    transformations(RoundedCornersTransformation(4.px.toFloat()))
                }
            }
        }
    }

    @CallbackProp
    fun click(onClickListener: OnClickListener?) {
        B.root.setOnClickListener(onClickListener)
    }
}
