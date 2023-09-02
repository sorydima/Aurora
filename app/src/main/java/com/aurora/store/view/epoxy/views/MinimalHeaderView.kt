package com.aurora.store.view.epoxy.views

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import com.aurora.store.R
import com.aurora.store.databinding.ViewActionHeaderBinding


@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class MinimalHeaderView : RelativeLayout {

    private lateinit var B: ViewActionHeaderBinding

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
        val view = inflate(context, R.layout.view_action_header, this)
        B = ViewActionHeaderBinding.bind(view)
    }

    @ModelProp
    fun title(title: String) {
        B.txtTitle.text = title
    }

    @CallbackProp
    fun click(onClickListener: OnClickListener?) {
        B.imgAction.setOnClickListener(onClickListener)
    }

    @OnViewRecycled
    fun clear() {
        B.imgAction.visibility = VISIBLE
    }
}
