package com.aurora.store.view.ui.sheets

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import coil.transform.CircleCropTransformation
import com.aurora.extensions.toast
import com.aurora.store.R
import com.aurora.store.databinding.SheetDeviceMiuiBinding

class DeviceMiuiSheet : BaseBottomSheet() {

    private lateinit var B: SheetDeviceMiuiBinding

    override fun onCreateContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?
    ): View {
        B = SheetDeviceMiuiBinding.inflate(inflater, container, false)

        inflateData()
        attachAction()

        return B.root
    }

    override fun onContentViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun inflateData() {
        B.imgIcon.load(R.drawable.ic_xiaomi_logo) {
            transformations(CircleCropTransformation())
        }
    }

    private fun attachAction() {
        B.btnPrimary.setOnClickListener {
            openDeveloperSettings()
        }

        B.btnSecondary.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }

    private fun openDeveloperSettings() {
        try {
            startActivity(Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS))
        } catch (e: Exception) {
            toast(R.string.toast_developer_setting_failed)
        }
    }
}
