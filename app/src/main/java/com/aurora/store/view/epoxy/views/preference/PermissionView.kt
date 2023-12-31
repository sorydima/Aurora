package com.aurora.store.view.epoxy.views.preference

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.aurora.extensions.getString
import com.aurora.store.R
import com.aurora.store.data.model.Permission
import com.aurora.store.databinding.ViewPermissionBinding
import com.aurora.store.view.epoxy.views.BaseView

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class PermissionView : RelativeLayout {

    private lateinit var B: ViewPermissionBinding

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
        val view = inflate(context, R.layout.view_permission, this)
        B = ViewPermissionBinding.bind(view)
    }

    @ModelProp
    fun permission(installer: Permission) {
        B.line1.text = installer.title
        B.line2.text = installer.subtitle
    }

    @ModelProp
    fun isGranted(granted: Boolean) {
        if (granted) {
            B.btnAction.isEnabled = false
            B.btnAction.text = getString(R.string.action_granted)
        } else {
            B.btnAction.isEnabled = true
            B.btnAction.text = getString(R.string.action_grant)
        }
    }

    @CallbackProp
    fun click(onClickListener: OnClickListener?) {
        B.btnAction.setOnClickListener(onClickListener)
    }
}
