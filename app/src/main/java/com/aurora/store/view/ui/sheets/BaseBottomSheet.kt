package com.aurora.store.view.ui.sheets

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.aurora.store.R
import com.aurora.store.databinding.SheetBaseBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Modifier

abstract class BaseBottomSheet : BottomSheetDialogFragment() {

    lateinit var VM: SheetBaseBinding

    var gson: Gson = GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT)
        .create()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = BottomSheetDialog(
            requireContext(),
            R.style.AppTheme_Dialog
        )

        VM = SheetBaseBinding.inflate(layoutInflater)

        bottomSheetDialog.setContentView(VM.root)

        val container = VM.container
        val contentView = onCreateContentView(
            LayoutInflater.from(requireContext()),
            container,
            savedInstanceState
        )

        if (contentView != null) {
            onContentViewCreated(contentView, savedInstanceState)
            container.addView(contentView)
        }

        bottomSheetDialog.setOnShowListener {
            val bottomSheet =
                bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            if (bottomSheet != null)
                BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
        }
        return bottomSheetDialog
    }

    abstract fun onCreateContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?
    ): View?

    abstract fun onContentViewCreated(view: View, savedInstanceState: Bundle?)
}
