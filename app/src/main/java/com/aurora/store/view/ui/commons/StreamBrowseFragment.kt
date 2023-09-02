package com.aurora.store.view.ui.commons

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aurora.gplayapi.data.models.StreamCluster
import com.aurora.store.R
import com.aurora.store.databinding.ActivityGenericRecyclerBinding
import com.aurora.store.view.custom.recycler.EndlessRecyclerOnScrollListener
import com.aurora.store.view.epoxy.views.AppProgressViewModel_
import com.aurora.store.view.epoxy.views.app.AppListViewModel_
import com.aurora.store.view.epoxy.views.shimmer.AppListViewShimmerModel_
import com.aurora.store.viewmodel.browse.StreamBrowseViewModel


class StreamBrowseFragment : BaseFragment(R.layout.activity_generic_recycler) {

    private var _binding: ActivityGenericRecyclerBinding? = null
    private val binding: ActivityGenericRecyclerBinding
        get() = _binding!!

    private val args: StreamBrowseFragmentArgs by navArgs()

    lateinit var VM: StreamBrowseViewModel

    lateinit var endlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener

    lateinit var title: String
    lateinit var cluster: StreamCluster

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ActivityGenericRecyclerBinding.bind(view)
        VM = ViewModelProvider(this)[StreamBrowseViewModel::class.java]

        // Toolbar
        binding.layoutToolbarAction.apply {
            txtTitle.text = args.title
            imgActionPrimary.setOnClickListener {
                findNavController().navigateUp()
            }
        }

        VM.liveData.observe(viewLifecycleOwner) {
            if (!::cluster.isInitialized) {
                endlessRecyclerOnScrollListener = object : EndlessRecyclerOnScrollListener() {
                    override fun onLoadMore(currentPage: Int) {
                        VM.nextCluster()
                    }
                }
                binding.recycler.addOnScrollListener(endlessRecyclerOnScrollListener)
            }

            cluster = it

            updateController(cluster)
            binding.layoutToolbarAction.txtTitle.text = it.clusterTitle
        }

        VM.getStreamBundle(args.browseUrl)
        updateController(null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateController(streamCluster: StreamCluster?) {
        binding.recycler.withModels {
            setFilterDuplicates(true)
            if (streamCluster == null) {
                for (i in 1..6) {
                    add(
                        AppListViewShimmerModel_()
                            .id(i)
                    )
                }
            } else {
                streamCluster.clusterAppList.forEach {
                    add(
                        AppListViewModel_()
                            .id(it.packageName.hashCode())
                            .app(it)
                            .click { _ -> openDetailsFragment(it) }
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
