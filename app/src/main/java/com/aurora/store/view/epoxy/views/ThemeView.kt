package com.aurora.store.view.epoxy.views

import android.content.Context
import android.util.AttributeSet
import android.widget.CompoundButton
import android.widget.RelativeLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.aurora.store.R
import com.aurora.store.data.model.Theme
import com.aurora.store.databinding.ViewThemeBinding

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class ThemeView : RelativeLayout {

    private lateinit var B: ViewThemeBinding

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
        val view = inflate(context, R.layout.view_theme, this)
        B = ViewThemeBinding.bind(view)
    }

    @ModelProp
    fun theme(theme: Theme) {
        B.line1.text = theme.title
        B.line2.text = theme.subtitle
    }

    @ModelProp
    fun markChecked(isChecked: Boolean) {
        B.radiobutton.isChecked = isChecked
    }

    @CallbackProp
    fun checked(onCheckedChangeListener: CompoundButton.OnCheckedChangeListener?) {
        B.radiobutton.setOnCheckedChangeListener(onCheckedChangeListener)
    }
}
