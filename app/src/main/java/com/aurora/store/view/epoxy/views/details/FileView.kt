package com.aurora.store.view.epoxy.views.details

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.aurora.gplayapi.data.models.File
import com.aurora.store.R
import com.aurora.store.databinding.ViewFileBinding
import com.aurora.store.util.CommonUtil
import com.aurora.store.view.epoxy.views.BaseView

@ModelView(
    autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class FileView : RelativeLayout {

    private lateinit var B: ViewFileBinding

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
        val view = inflate(context, R.layout.view_file, this)
        B = ViewFileBinding.bind(view)
    }

    @ModelProp
    fun file(file: File) {
        B.line1.text = file.name
        B.line2.text = CommonUtil.addSiPrefix(file.size)
    }
}
