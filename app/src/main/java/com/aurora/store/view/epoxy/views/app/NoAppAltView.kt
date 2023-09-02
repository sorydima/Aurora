package com.aurora.store.view.epoxy.views.app

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.aurora.store.R
import com.aurora.store.databinding.ViewNoAppAltBinding
import com.aurora.store.view.epoxy.views.BaseView


@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class NoAppAltView : RelativeLayout {

    private lateinit var B: ViewNoAppAltBinding

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
        val view = inflate(context, R.layout.view_no_app_alt, this)
        B = ViewNoAppAltBinding.bind(view)
    }

    @ModelProp
    fun message(message: String) {
        B.txtMsg.text = message
    }
}
