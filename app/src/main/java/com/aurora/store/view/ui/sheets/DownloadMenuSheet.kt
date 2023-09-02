package com.aurora.store.view.ui.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.aurora.extensions.copyToClipBoard
import com.aurora.extensions.toast
import com.aurora.store.R
import com.aurora.store.data.downloader.DownloadManager
import com.aurora.store.databinding.SheetDownloadMenuBinding
import com.tonyodev.fetch2.Fetch
import com.tonyodev.fetch2.Status
import kotlin.properties.Delegates

class DownloadMenuSheet : BaseBottomSheet() {

    private lateinit var B: SheetDownloadMenuBinding
    private lateinit var fetch: Fetch

    private val args: DownloadMenuSheetArgs by navArgs()
    private var status by Delegates.notNull<Int>()

    override fun onCreateContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?
    ): View {
        B = SheetDownloadMenuBinding.inflate(layoutInflater)
        return B.root
    }

    override fun onContentViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetch = DownloadManager
            .with(requireContext())
            .getFetchInstance()

        status = args.downloadFile.download.status.value
        attachNavigation()
    }

    private fun attachNavigation() {
        with(B.navigationView) {
            if (status == Status.PAUSED.value || status == Status.COMPLETED.value || status == Status.CANCELLED.value) {
                menu.findItem(R.id.action_pause).isVisible = false
            }

            if (status == Status.DOWNLOADING.value || status == Status.COMPLETED.value || status == Status.QUEUED.value) {
                menu.findItem(R.id.action_resume).isVisible = false
            }

            if (status == Status.COMPLETED.value || status == Status.CANCELLED.value) {
                menu.findItem(R.id.action_cancel).isVisible = false
            }

            setNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_copy -> {
                        requireContext().copyToClipBoard(args.downloadFile.download.url)
                        requireContext().toast(requireContext().getString(R.string.toast_clipboard_copied))
                    }
                    R.id.action_pause -> {
                        fetch.pause(args.downloadFile.download.id)
                    }
                    R.id.action_resume -> if (status == Status.FAILED.value || status == Status.CANCELLED.value) {
                        fetch.retry(args.downloadFile.download.id)
                    } else {
                        fetch.resume(args.downloadFile.download.id)
                    }
                    R.id.action_cancel -> {
                        fetch.cancel(args.downloadFile.download.id)
                    }
                    R.id.action_clear -> {
                        fetch.delete(args.downloadFile.download.id)
                    }
                }
                dismissAllowingStateLoss()
                false
            }
        }
    }
}
