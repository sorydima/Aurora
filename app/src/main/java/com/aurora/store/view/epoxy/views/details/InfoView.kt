package com.aurora.store.view.epoxy.views.details

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import com.aurora.store.R
import com.aurora.store.databinding.ViewInfoBinding
import com.aurora.store.view.epoxy.views.BaseView
import java.util.Locale

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class InfoView : RelativeLayout {

    private lateinit var B: ViewInfoBinding

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
        val view = inflate(context, R.layout.view_info, this)
        B = ViewInfoBinding.bind(view)
    }

    @ModelProp(options = [ModelProp.Option.IgnoreRequireHashCode])
    fun badge(info: Map.Entry<String, String>) {
        B.txtTitle.text = info.key
            .replace("_", " ")
            .lowercase(Locale.getDefault())
            .replaceFirstChar {
                if (it.isLowerCase()) {
                    it.titlecase(Locale.getDefault())
                } else {
                    it.toString()
                }
            }
        B.txtSubtitle.text = info.value
    }

    @OnViewRecycled
    fun clear() {
    }
}
