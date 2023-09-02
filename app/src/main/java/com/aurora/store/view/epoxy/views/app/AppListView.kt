package com.aurora.store.view.epoxy.views.app

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import coil.load
import coil.transform.RoundedCornersTransformation
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.aurora.extensions.getString
import com.aurora.gplayapi.data.models.App
import com.aurora.store.R
import com.aurora.store.databinding.ViewAppListBinding
import com.aurora.store.util.CommonUtil
import com.aurora.store.view.epoxy.views.BaseView

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class AppListView : RelativeLayout {

    private lateinit var B: ViewAppListBinding

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
        val view = inflate(context, R.layout.view_app_list, this)
        B = ViewAppListBinding.bind(view)
    }

    @ModelProp
    fun app(app: App) {
        B.imgIcon.load(app.iconArtwork.url) {
            placeholder(R.drawable.bg_placeholder)
            transformations(RoundedCornersTransformation(25F))
        }

        B.txtLine1.text = app.displayName
        B.txtLine2.text = app.developerName

        val extras: MutableList<String> = mutableListOf()
        extras.add(CommonUtil.addSiPrefix(app.size))
        extras.add("${app.labeledRating}★")
        extras.add(
            if (app.isFree)
                getString(R.string.details_free)
            else
                getString(R.string.details_paid)
        )

        if (app.containsAds)
            extras.add(getString(R.string.details_contains_ads))

        if (app.dependencies.dependentPackages.isNotEmpty())
            extras.add(getString(R.string.details_gsf_dependent))

        B.txtLine3.text = extras.joinToString(separator = "  •  ")
    }

    @CallbackProp
    fun click(onClickListener: OnClickListener?) {
        B.root.setOnClickListener(onClickListener)
    }

    @CallbackProp
    fun longClick(onClickListener: OnLongClickListener?) {
        B.root.setOnLongClickListener(onClickListener)
    }
}
