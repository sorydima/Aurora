package com.aurora.store.view.epoxy.views.details

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.text.HtmlCompat
import coil.load
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.aurora.gplayapi.data.models.details.Badge
import com.aurora.store.R
import com.aurora.store.databinding.ViewMoreBadgeBinding
import com.aurora.store.view.epoxy.views.BaseView

@ModelView(
    autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class MoreBadgeView : RelativeLayout {

    private lateinit var B: ViewMoreBadgeBinding

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
        val view = inflate(context, R.layout.view_more_badge, this)
        B = ViewMoreBadgeBinding.bind(view)
    }

    @ModelProp
    fun badge(badge: Badge) {
        B.line1.text = badge.textMajor

        badge.textMinorHtml?.let {
            if (it.isNotEmpty()) {
                B.line2.text = HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_COMPACT)
            } else {
                B.line2.text = badge.textMinor
            }
        }

        badge.textDescription?.let {
            if (it.isNotEmpty()) {
                B.line2.text = it
            }
        }

        badge.artwork?.let {
            B.img.load(it.url) {
                placeholder(R.drawable.ic_arrow_right)
            }
        }
    }
}
