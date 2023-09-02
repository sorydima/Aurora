package com.aurora.store.view.epoxy.views

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import coil.load
import coil.transform.RoundedCornersTransformation
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.aurora.gplayapi.SearchSuggestEntry
import com.aurora.store.R
import com.aurora.store.databinding.ViewSearchSuggestionBinding

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class SearchSuggestionView : RelativeLayout {

    private lateinit var B: ViewSearchSuggestionBinding

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
        val view = inflate(context, R.layout.view_search_suggestion, this)
        B = ViewSearchSuggestionBinding.bind(view)
    }

    @ModelProp
    fun entry(searchSuggestEntry: SearchSuggestEntry) {
        if (searchSuggestEntry.hasImageContainer()) {
            B.img.load(searchSuggestEntry.imageContainer.imageUrl) {
                transformations(RoundedCornersTransformation(8F))
            }
        } else {
            B.img.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_search_suggestion
                )
            )
        }

        B.txtTitle.text = searchSuggestEntry.title
    }

    @CallbackProp
    fun click(onClickListener: OnClickListener?) {
        B.root.setOnClickListener(onClickListener)
    }

    @CallbackProp
    fun action(onClickListener: OnClickListener?) {
        B.action.setOnClickListener(onClickListener)
    }
}
