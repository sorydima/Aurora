package com.aurora.store.view.epoxy.views.preference

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import coil.load
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.aurora.extensions.hide
import com.aurora.extensions.show
import com.aurora.store.R
import com.aurora.store.data.model.Link
import com.aurora.store.databinding.ViewLinkBinding
import com.aurora.store.view.epoxy.views.BaseView

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class LinkView : RelativeLayout {

    private lateinit var B: ViewLinkBinding

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
        val view = inflate(context, R.layout.view_link, this)
        B = ViewLinkBinding.bind(view)
    }

    @ModelProp
    fun link(link: Link) {
        B.line1.text = link.title
        B.line2.text = link.subtitle

        if (link.url.startsWith("http") || link.url.startsWith("upi")) {
            B.line3.hide()
        } else {
            B.line3.show()
            B.line3.text = link.url
        }

        B.imgIcon.load(link.icon)
    }

    @CallbackProp
    fun click(onClickListener: OnClickListener?) {
        B.root.setOnClickListener(onClickListener)
    }
}
