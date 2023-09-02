package com.aurora.store.view.ui.commons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.aurora.Constants
import com.aurora.gplayapi.data.models.App
import com.aurora.gplayapi.data.models.StreamBundle
import com.aurora.gplayapi.data.models.StreamCluster
import com.aurora.store.R
import com.aurora.store.data.ViewState
import com.aurora.store.databinding.FragmentForYouBinding
import com.aurora.store.view.custom.recycler.EndlessRecyclerOnScrollListener
import com.aurora.store.view.epoxy.controller.EarlyAccessCarouselController
import com.aurora.store.view.epoxy.controller.GenericCarouselController
import com.aurora.store.viewmodel.homestream.BaseClusterViewModel
import com.aurora.store.viewmodel.homestream.EarlyAccessAppsViewModel
import com.aurora.store.viewmodel.homestream.EarlyAccessGamesViewModel


class EarlyAccessFragment : BaseFragment(), GenericCarouselController.Callbacks {

    private lateinit var B: FragmentForYouBinding
    private lateinit var C: GenericCarouselController
    private lateinit var VM: BaseClusterViewModel

    private var pageType = 0

    companion object {
        @JvmStatic
        fun newInstance(pageType: Int): EarlyAccessFragment {
            return EarlyAccessFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constants.PAGE_TYPE, pageType)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        B = FragmentForYouBinding.bind(
            inflater.inflate(
                R.layout.fragment_for_you,
                container,
                false
            )
        )

        C = EarlyAccessCarouselController(this)

        val bundle = arguments
        if (bundle != null) {
            pageType = bundle.getInt(Constants.PAGE_TYPE, 0)
        }

        when (pageType) {
            0 -> VM = ViewModelProvider(this)[EarlyAccessAppsViewModel::class.java]
            1 -> VM = ViewModelProvider(this)[EarlyAccessGamesViewModel::class.java]
        }

        return B.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        B.recycler.setController(C)

        VM.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Empty -> {
                }
                is ViewState.Loading -> {
                }
                is ViewState.Error -> {
                }
                is ViewState.Success<*> -> {
                    updateController(it.data as StreamBundle)
                }
                else -> {

                }
            }
        }

        B.recycler.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore(currentPage: Int) {
                VM.observe()
            }
        })
    }

    private fun updateController(streamBundle: StreamBundle) {
        C.setData(streamBundle)
    }

    override fun onHeaderClicked(streamCluster: StreamCluster) {
        if (streamCluster.clusterBrowseUrl.isNotEmpty())
            openStreamBrowseFragment(streamCluster.clusterBrowseUrl)
    }

    override fun onClusterScrolled(streamCluster: StreamCluster) {
        VM.observeCluster(streamCluster)
    }

    override fun onAppClick(app: App) {
        openDetailsFragment(app)
    }

    override fun onAppLongClick(app: App) {
        Toast.makeText(requireContext(), app.displayName, Toast.LENGTH_SHORT).show()
    }
}
