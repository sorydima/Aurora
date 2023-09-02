package com.aurora.store.view.epoxy.views.details

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import coil.load
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.aurora.gplayapi.data.models.details.Badge
import com.aurora.store.R
import com.aurora.store.databinding.ViewBadgeBinding
import com.aurora.store.view.epoxy.views.BaseView

@ModelView(
    autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class BadgeView : RelativeLayout {

    private lateinit var B: ViewBadgeBinding

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
        val view = inflate(context, R.layout.view_badge, this)
        B = ViewBadgeBinding.bind(view)
    }

    @ModelProp
    fun badge(badge: Badge) {
        if (badge.textMajor.isEmpty()) {
            if (badge.textMinor.isEmpty()) {
                B.txt.text = badge.textDescription
            } else {
                B.txt.text = badge.textMinor
            }
        } else {
            B.txt.text = badge.textMajor
        }

        badge.artwork?.let {
            B.img.load(it.url)
        }
    }
}
