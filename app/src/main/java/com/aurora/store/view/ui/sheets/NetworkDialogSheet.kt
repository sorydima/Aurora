package com.aurora.store.view.ui.sheets

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aurora.extensions.isQAndAbove
import com.aurora.store.databinding.SheetNetworkBinding

class NetworkDialogSheet : BaseBottomSheet() {

    private val TAG = NetworkDialogSheet::class.java.simpleName

    lateinit var B: SheetNetworkBinding

    companion object {

        const val TAG = "NetworkDialogSheet"

        @JvmStatic
        fun newInstance(): NetworkDialogSheet {
            return NetworkDialogSheet().apply {

            }
        }
    }

    override fun onCreateContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?
    ): View {
        B = SheetNetworkBinding.inflate(inflater, container, false)
        return B.root
    }

    override fun onContentViewCreated(view: View, savedInstanceState: Bundle?) {
        B.btnAction.setOnClickListener {
            if (isQAndAbove()) {
                startActivity(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY))
            } else {
                try {
                    startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
                } catch (exception: ActivityNotFoundException) {
                    Log.i(TAG, "Unable to launch wireless settings")
                    startActivity(Intent(Settings.ACTION_SETTINGS))
                }
            }
        }
    }
}
