package com.aurora.store.view.ui.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aurora.Constants
import com.aurora.extensions.browse
import com.aurora.extensions.toast
import com.aurora.store.R
import com.aurora.store.databinding.SheetTosBinding
import com.aurora.store.util.Preferences

class TOSSheet : BaseBottomSheet() {

    private lateinit var B: SheetTosBinding

    override fun onStart() {
        super.onStart()
        isCancelable = false
    }

    override fun onCreateContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?
    ): View {
        B = SheetTosBinding.inflate(inflater, container, false)

        attachAction()

        return B.root
    }

    override fun onContentViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun attachAction() {
        B.btnRead.setOnClickListener {
            requireContext().browse(Constants.TOS_URL)
        }

        B.btnPrimary.setOnClickListener {
            if (B.checkboxAccept.isChecked) {
                Preferences.putBoolean(requireContext(), Preferences.PREFERENCE_TOS_READ, true)
                dismissAllowingStateLoss()
            } else {
                toast(R.string.onboarding_tos_error)
            }
        }

        B.btnSecondary.setOnClickListener {
            requireActivity().finish()
        }
    }
}
