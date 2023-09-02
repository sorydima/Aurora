package com.aurora.store.view.ui.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.aurora.extensions.copyToClipBoard
import com.aurora.extensions.toast
import com.aurora.store.R
import com.aurora.store.databinding.SheetInstallErrorBinding

class InstallErrorDialogSheet : BaseBottomSheet() {

    private lateinit var B: SheetInstallErrorBinding

    private val args: InstallErrorDialogSheetArgs by navArgs()

    override fun onCreateContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?
    ): View {
        B = SheetInstallErrorBinding.inflate(inflater, container, false)
        inflateData()
        attachAction()

        return B.root
    }

    override fun onContentViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun inflateData() {
        B.imgIcon.load(args.app.iconArtwork.url) {
            transformations(CircleCropTransformation())
        }

        B.txtLine1.text = args.app.displayName
        B.txtLine2.text = args.error
        B.txtLine3.text = args.extra
    }

    private fun attachAction() {
        B.btnPrimary.setOnClickListener {
            dismissAllowingStateLoss()
        }

        B.btnSecondary.setOnClickListener {
            requireContext().copyToClipBoard(args.extra)
            requireContext().toast(R.string.toast_clipboard_copied)
        }
    }
}
