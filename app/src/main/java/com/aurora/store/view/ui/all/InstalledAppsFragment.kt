package com.aurora.store.view.ui.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.aurora.gplayapi.data.models.App
import com.aurora.store.R
import com.aurora.store.databinding.FragmentUpdatesBinding
import com.aurora.store.view.epoxy.views.HeaderViewModel_
import com.aurora.store.view.epoxy.views.app.AppListViewModel_
import com.aurora.store.view.epoxy.views.shimmer.AppListViewShimmerModel_
import com.aurora.store.view.ui.commons.BaseFragment
import com.aurora.store.viewmodel.all.InstalledViewModel

class InstalledAppsFragment : BaseFragment() {

    private lateinit var VM: InstalledViewModel
    private lateinit var B: FragmentUpdatesBinding

    companion object {
        @JvmStatic
        fun newInstance(): InstalledAppsFragment {
            return InstalledAppsFragment().apply {

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        B = FragmentUpdatesBinding.bind(
            inflater.inflate(
                R.layout.fragment_updates,
                container,
                false
            )
        )

        VM = ViewModelProvider(requireActivity()).get(InstalledViewModel::class.java)

        return B.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        VM.liveData.observe(viewLifecycleOwner) {
            updateController(it)
            B.swipeRefreshLayout.isRefreshing = false
        }

        B.swipeRefreshLayout.setOnRefreshListener {
            VM.observe()
        }

        updateController(null)
    }

    private fun updateController(appList: List<App>?) {
        B.recycler.withModels {
            setFilterDuplicates(true)
            if (appList == null) {
                for (i in 1..6) {
                    add(
                        AppListViewShimmerModel_()
                            .id(i)
                    )
                }
            } else {
                add(
                    HeaderViewModel_()
                        .id("header")
                        .title("${appList.size} apps installed")
                )
                appList.forEach { app ->
                    add(
                        AppListViewModel_()
                            .id(app.id)
                            .app(app)
                            .click { _ -> openDetailsFragment(app) }
                            .longClick { _ ->
                                openAppMenuSheet(app)
                                false
                            }
                    )
                }
            }
        }
    }
}
