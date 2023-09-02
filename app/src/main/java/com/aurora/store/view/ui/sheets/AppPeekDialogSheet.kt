package com.aurora.store.view.ui.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.aurora.store.R
import com.aurora.store.databinding.SheetAppPeekBinding
import com.aurora.store.util.CommonUtil

class AppPeekDialogSheet : BaseBottomSheet() {

    lateinit var B: SheetAppPeekBinding

    private val args: AppPeekDialogSheetArgs by navArgs()

    override fun onCreateContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?
    ): View {
        B = SheetAppPeekBinding.inflate(inflater, container, false)
        return B.root
    }

    override fun onContentViewCreated(view: View, savedInstanceState: Bundle?) {
        B.txtLine1.text = args.app.displayName
        B.imgIcon.load(args.app.iconArtwork.url) {
            transformations(RoundedCornersTransformation(25F))
        }
        B.txtLine2.text = args.app.developerName
        B.txtLine3.text = String.format(
            requireContext().getString(R.string.app_list_rating),
            CommonUtil.addSiPrefix(args.app.size),
            args.app.labeledRating,
            if (args.app.isFree)
                "Free"
            else
                "Paid"
        )
    }
}
