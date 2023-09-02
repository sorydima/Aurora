package com.aurora.store.view.epoxy.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.aurora.store.R
import com.aurora.store.data.model.Accent
import com.aurora.store.databinding.ViewAccentBinding

@ModelView(
    autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class AccentView : RelativeLayout {

    private lateinit var B: ViewAccentBinding

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
        val view = inflate(context, R.layout.view_accent, this)
        B = ViewAccentBinding.bind(view)
    }

    @ModelProp
    fun accent(accent: Accent) {
        if (accent.accent.isBlank()) {
            B.img.background = ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.ic_settings_suggest,
                context.theme
            )
        } else {
            B.img.backgroundTintList = ColorStateList.valueOf(Color.parseColor(accent.accent))
        }
    }


    @ModelProp
    fun markChecked(isChecked: Boolean) {
        B.tick.isVisible = isChecked
    }

    @CallbackProp
    fun click(onClickListener: OnClickListener?) {
        B.root.setOnClickListener(onClickListener)
    }
}
