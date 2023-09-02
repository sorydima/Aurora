package com.aurora.store.view.epoxy.views.app

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.aurora.store.R
import com.aurora.store.databinding.ViewNoAppBinding
import com.aurora.store.view.epoxy.views.BaseView


@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_MATCH_HEIGHT,
    baseModelClass = BaseView::class
)
class NoAppView : RelativeLayout {

    private lateinit var B: ViewNoAppBinding

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
        val view = inflate(context, R.layout.view_no_app, this)
        B = ViewNoAppBinding.bind(view)
    }

    @ModelProp
    fun message(message: String) {
        B.txt.text = message
    }

    @ModelProp
    fun icon(icon: Int?) {
        icon?.let {
            B.img.setImageDrawable(ContextCompat.getDrawable(context, icon))
        }
    }
}
