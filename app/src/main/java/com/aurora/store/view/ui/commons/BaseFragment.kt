package com.aurora.store.view.ui.commons

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aurora.gplayapi.data.models.App
import com.aurora.gplayapi.data.models.Category
import com.aurora.store.MobileNavigationDirections
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Modifier

open class BaseFragment : Fragment {

    constructor(): super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    var gson: Gson = GsonBuilder().excludeFieldsWithModifiers(
        Modifier.TRANSIENT
    ).create()

    fun openDetailsFragment(app: App) {
        findNavController().navigate(
            MobileNavigationDirections.actionGlobalAppDetailsFragment(app.packageName, app)
        )
    }

    fun openCategoryBrowseFragment(category: Category) {
        findNavController().navigate(
            MobileNavigationDirections.actionGlobalCategoryBrowseFragment(
                category.title,
                category.browseUrl
            )
        )
    }

    fun openStreamBrowseFragment(browseUrl: String, title: String = "") {
        if (browseUrl.lowercase().contains("expanded")) {
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalExpandedStreamBrowseFragment(
                    title,
                    browseUrl
                )
            )
        } else if (browseUrl.lowercase().contains("developer")) {
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalDevProfileFragment(
                    browseUrl.substringAfter("developer-"),
                    title
                )
            )
        } else {
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalStreamBrowseFragment(
                    browseUrl,
                    title
                )
            )
        }
    }

    fun openEditorStreamBrowseFragment(browseUrl: String, title: String = "") {
        findNavController().navigate(
            MobileNavigationDirections.actionGlobalEditorStreamBrowseFragment(title, browseUrl)
        )
    }

    fun openScreenshotFragment(app: App, position: Int) {
        findNavController().navigate(
            MobileNavigationDirections.actionGlobalScreenshotFragment(
                position,
                app.screenshots.toTypedArray()
            )
        )
    }

    fun openAppMenuSheet(app: App) {
        findNavController().navigate(MobileNavigationDirections.actionGlobalAppMenuSheet(app))
    }
}
