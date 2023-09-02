package com.aurora.store.view.epoxy.views.preference

import android.content.Context
import android.util.AttributeSet
import android.widget.CompoundButton
import android.widget.RelativeLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.aurora.store.R
import com.aurora.store.databinding.ViewLocaleBinding
import com.aurora.store.view.epoxy.views.BaseView
import java.util.Locale

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class LocaleView : RelativeLayout {

    private lateinit var B: ViewLocaleBinding

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
        val view = inflate(context, R.layout.view_locale, this)
        B = ViewLocaleBinding.bind(view)
    }

    @ModelProp
    fun locale(locale: Locale) {
        B.line1.text = locale.displayName
        B.line2.text = if (locale.displayCountry.isNotEmpty())
            locale.displayCountry
        else
            locale.displayLanguage
    }

    @ModelProp
    fun markChecked(isChecked: Boolean) {
        B.checkbox.isChecked = isChecked
    }

    @CallbackProp
    fun checked(onCheckedChangeListener: CompoundButton.OnCheckedChangeListener?) {
        B.checkbox.setOnCheckedChangeListener(onCheckedChangeListener)
    }
}
