package com.aurora.store.view.epoxy.views.app

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import coil.load
import coil.transform.RoundedCornersTransformation
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import com.aurora.extensions.invisible
import com.aurora.extensions.px
import com.aurora.extensions.show
import com.aurora.store.R
import com.aurora.store.State
import com.aurora.store.data.model.UpdateFile
import com.aurora.store.databinding.ViewAppUpdateBinding
import com.aurora.store.util.CommonUtil
import com.aurora.store.view.epoxy.views.BaseView

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    baseModelClass = BaseView::class
)
class AppUpdateView : RelativeLayout {

    private lateinit var B: ViewAppUpdateBinding

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
        val view = inflate(context, R.layout.view_app_update, this)
        B = ViewAppUpdateBinding.bind(view)
    }

    @ModelProp
    fun updateFile(updateFile: UpdateFile?) {
        if (updateFile != null) {

            /*Inflate App details*/
            with(updateFile.app) {
                B.txtLine1.text = displayName
                B.imgIcon.load(iconArtwork.url) {
                    placeholder(R.drawable.bg_placeholder)
                    transformations(RoundedCornersTransformation(8.px.toFloat()))
                }

                B.txtLine2.text = developerName
                B.txtLine3.text = ("${CommonUtil.addSiPrefix(size)}  •  $updatedOn")
                B.txtLine4.text = ("$versionName (${versionCode})")
                B.txtChangelog.text = if (changes.isNotEmpty())
                    HtmlCompat.fromHtml(
                        changes,
                        HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
                    )
                else
                    context.getString(R.string.details_changelog_unavailable)

                B.headerIndicator.setOnClickListener {
                    if (B.txtChangelog.isVisible) {
                        B.headerIndicator.load(R.drawable.ic_arrow_down)
                        B.txtChangelog.visibility = View.GONE
                    } else {
                        B.headerIndicator.load(R.drawable.ic_arrow_up)
                        B.txtChangelog.visibility = View.VISIBLE
                    }
                }
            }

            /*Inflate Download details*/
            updateFile.group?.let {
                when (updateFile.state) {
                    State.QUEUED -> {
                        B.progressDownload.progress = 0
                        B.progressDownload.show()
                        B.btnAction.updateState(State.QUEUED)
                    }
                    State.IDLE, State.CANCELED -> {
                        B.progressDownload.progress = 0
                        B.progressDownload.invisible()
                        B.btnAction.updateState(State.IDLE)
                    }
                    State.PROGRESS -> {
                        val progress = it.groupDownloadProgress
                        if (progress > 0) {
                            if (progress == 100) {
                                B.progressDownload.invisible()
                            } else {
                                B.progressDownload.progress = progress
                                B.progressDownload.show()
                            }
                        }
                    }
                    State.COMPLETE -> {
                        B.progressDownload.invisible()
                        B.btnAction.updateState(State.COMPLETE)
                    }
                }
            }
        }
    }

    @ModelProp
    fun state(state: State?) {
        state?.let {
            B.btnAction.updateState(it)
        }
    }

    @CallbackProp
    fun click(onClickListener: OnClickListener?) {
        B.layoutContent.setOnClickListener(onClickListener)
    }

    @CallbackProp
    fun positiveAction(onClickListener: OnClickListener?) {
        B.btnAction.addPositiveOnClickListener(onClickListener)
    }

    @CallbackProp
    fun negativeAction(onClickListener: OnClickListener?) {
        B.btnAction.addNegativeOnClickListener(onClickListener)
    }

    @CallbackProp
    fun installAction(onClickListener: OnClickListener?) {
        B.btnAction.addInstallOnClickListener(onClickListener)
    }

    @CallbackProp
    fun longClick(onClickListener: OnLongClickListener?) {
        B.layoutContent.setOnLongClickListener(onClickListener)
    }

    @OnViewRecycled
    fun clear() {
        B.headerIndicator.removeCallbacks { }
        B.progressDownload.invisible()
    }
}
