package com.aurora.store.view.epoxy.views.preference

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.aurora.store.R
import com.aurora.store.data.model.Dash
import com.aurora.store.databinding.ViewDashBinding
import com.aurora.store.view.epoxy.views.BaseView
import com.aurora.store.view.ui.onboarding.WelcomeFragment

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class DashView : RelativeLayout {

    private lateinit var B: ViewDashBinding

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
        val view = inflate(context, R.layout.view_dash, this)
        B = ViewDashBinding.bind(view)
    }

    @ModelProp
    fun dash(dash: Dash) {
        B.line1.text = dash.title
        B.line2.text = dash.subtitle

        var icon = WelcomeFragment.icMap[dash.icon]
        if (icon == null)
            icon = R.drawable.ic_arrow_right
        B.img.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                icon
            )
        )
    }

    @CallbackProp
    fun click(onClickListener: OnClickListener?) {
        B.root.setOnClickListener(onClickListener)
    }
}
