package com.aurora.store.view.ui.spoof

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aurora.extensions.toast
import com.aurora.store.R
import com.aurora.store.data.providers.NativeDeviceInfoProvider
import com.aurora.store.data.providers.SpoofProvider
import com.aurora.store.databinding.FragmentGenericRecyclerBinding
import com.aurora.store.view.epoxy.views.preference.DeviceViewModel_
import com.aurora.store.view.ui.commons.BaseFragment
import com.aurora.store.viewmodel.spoof.SpoofViewModel
import java.util.Properties
import kotlinx.coroutines.launch


class DeviceSpoofFragment : BaseFragment() {

    private lateinit var B: FragmentGenericRecyclerBinding
    private lateinit var spoofProvider: SpoofProvider

    private var properties: Properties = Properties()

    private val viewModel: SpoofViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance(): DeviceSpoofFragment {
            return DeviceSpoofFragment().apply {

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        B = FragmentGenericRecyclerBinding.bind(
            inflater.inflate(
                R.layout.fragment_generic_recycler,
                container,
                false
            )
        )

        properties = NativeDeviceInfoProvider(requireContext()).getNativeDeviceProperties()
        spoofProvider = SpoofProvider(requireContext())

        return B.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (spoofProvider.isDeviceSpoofEnabled())
            properties = spoofProvider.getSpoofDeviceProperties()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.availableDevices.collect { updateController(it) }
            }
        }
        viewModel.fetchAvailableDevices(view.context)
    }

    private fun updateController(locales: List<Properties>) {
        B.recycler.withModels {
            setFilterDuplicates(true)
            locales
                .sortedBy { it.getProperty("UserReadableName") }
                .forEach {
                    add(
                        DeviceViewModel_()
                            .id(it.hashCode())
                            .markChecked(
                                properties.getProperty("UserReadableName") == it.getProperty(
                                    "UserReadableName"
                                )
                            )
                            .checked { _, checked ->
                                if (checked) {
                                    properties = it
                                    saveSelection(it)
                                    requestModelBuild()
                                }
                            }
                            .properties(it)
                    )
                }
        }
    }

    private fun saveSelection(properties: Properties) {
        requireContext().toast(R.string.spoof_apply)
        spoofProvider.setSpoofDeviceProperties(properties)
    }
}
