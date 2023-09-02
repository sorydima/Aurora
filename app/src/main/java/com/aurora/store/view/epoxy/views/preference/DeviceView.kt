package com.aurora.store.view.epoxy.views.preference

import android.content.Context
import android.util.AttributeSet
import android.widget.CompoundButton
import android.widget.RelativeLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.aurora.store.R
import com.aurora.store.databinding.ViewDeviceBinding
import com.aurora.store.view.epoxy.views.BaseView
import java.util.Properties

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class DeviceView : RelativeLayout {

    private lateinit var B: ViewDeviceBinding

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
        val view = inflate(context, R.layout.view_device, this)
        B = ViewDeviceBinding.bind(view)
    }

    @ModelProp
    fun properties(properties: Properties) {
        B.line1.text = properties.getProperty("UserReadableName")
        B.line2.text = resources.getString(
            R.string.spoof_property,
            properties.getProperty("Build.MANUFACTURER"),
            properties.getProperty("Build.VERSION.SDK_INT")
        )
        B.line3.text = properties.getProperty("Platforms")
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
