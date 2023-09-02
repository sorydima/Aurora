package com.aurora.store.view.ui.commons

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aurora.gplayapi.data.models.App
import com.aurora.gplayapi.data.models.StreamBundle
import com.aurora.gplayapi.data.models.StreamCluster
import com.aurora.store.R
import com.aurora.store.data.ViewState
import com.aurora.store.databinding.ActivityGenericRecyclerBinding
import com.aurora.store.view.custom.recycler.EndlessRecyclerOnScrollListener
import com.aurora.store.view.epoxy.controller.CategoryCarouselController
import com.aurora.store.view.epoxy.controller.GenericCarouselController
import com.aurora.store.viewmodel.subcategory.SubCategoryClusterViewModel


class CategoryBrowseFragment : BaseFragment(R.layout.activity_generic_recycler),
    GenericCarouselController.Callbacks {

    private var _binding: ActivityGenericRecyclerBinding? = null
    private val binding: ActivityGenericRecyclerBinding
        get() = _binding!!

    private val args: CategoryBrowseFragmentArgs by navArgs()

    lateinit var C: GenericCarouselController
    lateinit var VM: SubCategoryClusterViewModel

    lateinit var endlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = ActivityGenericRecyclerBinding.bind(view)
        C = CategoryCarouselController(this)
        VM = ViewModelProvider(this)[SubCategoryClusterViewModel::class.java]

        // Toolbar
        binding.layoutToolbarAction.apply {
            txtTitle.text = args.title
            imgActionPrimary.setOnClickListener { findNavController().navigateUp() }
        }

        // RecyclerView
        binding.recycler.setController(C)

        VM.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Empty -> {
                }

                is ViewState.Loading -> {
                    updateController(null)
                }

                is ViewState.Error -> {

                }

                is ViewState.Success<*> -> {
                    updateController(it.data as StreamBundle)
                }

                else -> {}
            }
        }

        endlessRecyclerOnScrollListener =
            object : EndlessRecyclerOnScrollListener() {
                override fun onLoadMore(currentPage: Int) {
                    VM.observe()
                }
            }

        endlessRecyclerOnScrollListener.disable()
        binding.recycler.addOnScrollListener(endlessRecyclerOnScrollListener)

        VM.observeCategory(args.browseUrl)
        updateController(null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateController(streamBundle: StreamBundle?) {
        if (streamBundle != null)
            endlessRecyclerOnScrollListener.enable()
        C.setData(streamBundle)
    }

    override fun onHeaderClicked(streamCluster: StreamCluster) {
        if (streamCluster.clusterBrowseUrl.isNotEmpty())
            openStreamBrowseFragment(streamCluster.clusterBrowseUrl)
        else
            Toast.makeText(
                requireContext(),
                getString(R.string.toast_page_unavailable),
                Toast.LENGTH_SHORT
            )
                .show()
    }

    override fun onClusterScrolled(streamCluster: StreamCluster) {
        VM.observeCluster(streamCluster)
    }

    override fun onAppClick(app: App) {
        openDetailsFragment(app)
    }

    override fun onAppLongClick(app: App) {
        findNavController().navigate(
            CategoryBrowseFragmentDirections.actionCategoryBrowseFragmentToAppPeekDialogSheet(app)
        )
    }
}
