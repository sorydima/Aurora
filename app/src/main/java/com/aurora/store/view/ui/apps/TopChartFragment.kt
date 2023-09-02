package com.aurora.store.view.ui.apps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.aurora.Constants
import com.aurora.gplayapi.data.models.StreamCluster
import com.aurora.store.R
import com.aurora.store.databinding.FragmentTopContainerBinding
import com.aurora.store.view.custom.recycler.EndlessRecyclerOnScrollListener
import com.aurora.store.view.epoxy.views.AppProgressViewModel_
import com.aurora.store.view.epoxy.views.app.AppListViewModel_
import com.aurora.store.view.epoxy.views.shimmer.AppListViewShimmerModel_
import com.aurora.store.view.ui.commons.BaseFragment
import com.aurora.store.viewmodel.topchart.BaseChartViewModel
import com.aurora.store.viewmodel.topchart.TopFreeAppChartViewModel
import com.aurora.store.viewmodel.topchart.TopGrossingAppChartViewModel
import com.aurora.store.viewmodel.topchart.TopPaidAppChartViewModel
import com.aurora.store.viewmodel.topchart.TrendingAppChartViewModel

class TopChartFragment : BaseFragment() {

    private lateinit var VM: BaseChartViewModel
    private lateinit var B: FragmentTopContainerBinding

    private lateinit var streamCluster: StreamCluster

    private var chartType = 0
    private var chartCategory = 0

    companion object {
        @JvmStatic
        fun newInstance(chartType: Int, chartCategory: Int): TopChartFragment {
            return TopChartFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constants.TOP_CHART_TYPE, chartType)
                    putInt(Constants.TOP_CHART_CATEGORY, chartCategory)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        B = FragmentTopContainerBinding.bind(
            inflater.inflate(
                R.layout.fragment_top_container,
                container,
                false
            )
        )

        val bundle = arguments
        if (bundle != null) {
            chartType = bundle.getInt(Constants.TOP_CHART_TYPE, 0)
            chartCategory = bundle.getInt(Constants.TOP_CHART_CATEGORY, 0)
        }

        when (chartCategory) {
            0 -> VM = ViewModelProvider(this)[TopFreeAppChartViewModel::class.java]
            1 -> VM = ViewModelProvider(this)[TopGrossingAppChartViewModel::class.java]
            2 -> VM = ViewModelProvider(this)[TrendingAppChartViewModel::class.java]
            3 -> VM = ViewModelProvider(this)[TopPaidAppChartViewModel::class.java]
        }

        return B.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        VM.liveData.observe(viewLifecycleOwner) {
            if (!::streamCluster.isInitialized)
                attachRecycler()

            streamCluster = it

            updateController(streamCluster)
        }

        updateController(null)
    }

    private fun attachRecycler() {
        B.recycler.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore(currentPage: Int) {
                VM.nextCluster()
            }
        })
    }

    private fun updateController(streamCluster: StreamCluster?) {
        B.recycler.withModels {
            setFilterDuplicates(true)
            if (streamCluster == null) {
                for (i in 1..6) {
                    add(
                        AppListViewShimmerModel_()
                            .id(i)
                    )
                }
            } else {
                streamCluster.clusterAppList.forEach { app ->
                    add(
                        AppListViewModel_()
                            .id(app.id)
                            .app(app)
                            .click { _ -> openDetailsFragment(app) }
                    )
                }

                if (streamCluster.hasNext()) {
                    add(
                        AppProgressViewModel_()
                            .id("progress")
                    )
                }
            }
        }
    }
}
