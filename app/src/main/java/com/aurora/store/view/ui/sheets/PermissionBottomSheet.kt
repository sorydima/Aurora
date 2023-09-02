package com.aurora.store.view.ui.sheets

import android.content.pm.PackageManager
import android.content.pm.PermissionGroupInfo
import android.content.pm.PermissionInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.navArgs
import com.aurora.extensions.hide
import com.aurora.extensions.show
import com.aurora.store.R
import com.aurora.store.databinding.SheetPermissionsBinding
import com.aurora.store.view.custom.layouts.PermissionGroup
import java.util.*

class PermissionBottomSheet : BaseBottomSheet() {

    private lateinit var B: SheetPermissionsBinding
    private lateinit var packageManager: PackageManager

    private val args: PermissionBottomSheetArgs by navArgs()

    override fun onCreateContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?
    ): View {
        B = SheetPermissionsBinding.inflate(inflater)
        return B.root
    }

    override fun onContentViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateData()
    }

    private fun inflateData() {
        packageManager = requireContext().packageManager

        val permissionGroupWidgets: MutableMap<String, PermissionGroup?> =
            HashMap<String, PermissionGroup?>()
        for (permissionName in args.app.permissions) {

            val permissionInfo = getPermissionInfo(permissionName) ?: continue
            val permissionGroupInfo = getPermissionGroupInfo(permissionInfo)
            var permissionGroup: PermissionGroup?

            if (permissionGroupWidgets.containsKey(permissionGroupInfo.name)) {
                permissionGroup = permissionGroupWidgets[permissionGroupInfo.name]
            } else {
                permissionGroup = PermissionGroup(context)
                permissionGroup.setPermissionGroupInfo(permissionGroupInfo)
                permissionGroup.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                permissionGroupWidgets[permissionGroupInfo.name] = permissionGroup
            }

            permissionGroup?.addPermission(permissionInfo)
        }

        B.permissionsContainer.removeAllViews()

        val permissionGroupLabels: List<String> = ArrayList(permissionGroupWidgets.keys)
        permissionGroupLabels.sortedBy { it }.forEach {
            B.permissionsContainer.addView(permissionGroupWidgets[it])
        }

        if (permissionGroupLabels.isEmpty())
            B.permissionsNone.show()
        else
            B.permissionsNone.hide()
    }

    private fun getPermissionInfo(permissionName: String): PermissionInfo? {
        return try {
            packageManager.getPermissionInfo(permissionName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }

    private fun getPermissionGroupInfo(permissionInfo: PermissionInfo): PermissionGroupInfo {
        val permissionGroupInfo: PermissionGroupInfo = if (null == permissionInfo.group) {
            getFakePermissionGroupInfo(permissionInfo.packageName)
        } else {
            try {
                packageManager.getPermissionGroupInfo(permissionInfo.group!!, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                getFakePermissionGroupInfo(permissionInfo.packageName)
            }
        }
        if (permissionGroupInfo.icon == 0) {
            permissionGroupInfo.icon = R.drawable.ic_permission_android
        }
        return permissionGroupInfo
    }

    private fun getFakePermissionGroupInfo(packageName: String): PermissionGroupInfo {
        val permissionGroupInfo = PermissionGroupInfo()
        when (packageName) {
            "android" -> {
                permissionGroupInfo.icon = R.drawable.ic_permission_android
                permissionGroupInfo.name = "android"
            }
            "com.google.android.gsf", "com.android.vending" -> {
                permissionGroupInfo.icon = R.drawable.ic_permission_google
                permissionGroupInfo.name = "google"
            }
            else -> {
                permissionGroupInfo.icon = R.drawable.ic_permission_unknown
                permissionGroupInfo.name = "unknown"
            }
        }
        return permissionGroupInfo
    }
}
