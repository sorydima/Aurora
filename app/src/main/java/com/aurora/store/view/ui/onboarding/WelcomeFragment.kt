package com.aurora.store.view.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aurora.extensions.browse
import com.aurora.store.R
import com.aurora.store.data.model.Dash
import com.aurora.store.databinding.FragmentOnboardingWelcomeBinding
import com.aurora.store.view.epoxy.views.preference.DashViewModel_
import com.aurora.store.view.ui.commons.BaseFragment
import com.google.gson.reflect.TypeToken
import java.nio.charset.StandardCharsets

class WelcomeFragment : BaseFragment() {

    private lateinit var B: FragmentOnboardingWelcomeBinding

    companion object {
        @JvmStatic
        fun newInstance(): WelcomeFragment {
            return WelcomeFragment().apply {

            }
        }

        val icMap: MutableMap<String, Int> = mutableMapOf(
            "ic_faq" to R.drawable.ic_faq,
            "ic_code" to R.drawable.ic_code,
            "ic_license" to R.drawable.ic_license,
            "ic_privacy" to R.drawable.ic_privacy,
            "ic_disclaimer" to R.drawable.ic_disclaimer,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        B = FragmentOnboardingWelcomeBinding.bind(
            inflater.inflate(
                R.layout.fragment_onboarding_welcome,
                container,
                false
            )
        )

        return B.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dashList = loadDashFromAssets()
        updateController(dashList)
    }

    private fun updateController(dashList: List<Dash>) {
        B.epoxyRecycler.withModels {
            setFilterDuplicates(true)
            dashList.forEach {
                add(
                    DashViewModel_()
                        .id(it.id)
                        .dash(it)
                        .click { _ -> requireContext().browse(it.url) }
                )
            }
        }
    }

    private fun loadDashFromAssets(): List<Dash> {
        val inputStream = requireContext().assets.open("dash.json")
        val bytes = ByteArray(inputStream.available())
        inputStream.read(bytes)
        inputStream.close()

        val json = String(bytes, StandardCharsets.UTF_8)
        return gson.fromJson<MutableList<Dash>?>(
            json,
            object : TypeToken<MutableList<Dash?>?>() {}.type
        )
    }
}