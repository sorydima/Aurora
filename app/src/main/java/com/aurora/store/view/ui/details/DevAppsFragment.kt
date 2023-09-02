package com.aurora.store.view.ui.details

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aurora.gplayapi.data.models.SearchBundle
import com.aurora.store.R
import com.aurora.store.databinding.ActivityGenericRecyclerBinding
import com.aurora.store.view.custom.recycler.EndlessRecyclerOnScrollListener
import com.aurora.store.view.epoxy.views.AppProgressViewModel_
import com.aurora.store.view.epoxy.views.app.AppListViewModel_
import com.aurora.store.view.ui.commons.BaseFragment
import com.aurora.store.viewmodel.search.SearchResultViewModel

class DevAppsFragment : BaseFragment(R.layout.activity_generic_recycler) {

    private var _binding: ActivityGenericRecyclerBinding? = null
    private val binding: ActivityGenericRecyclerBinding
        get() = _binding!!

    private val args: DevAppsFragmentArgs by navArgs()

    private lateinit var VM: SearchResultViewModel

    private lateinit var endlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ActivityGenericRecyclerBinding.bind(view)
        VM = ViewModelProvider(this)[SearchResultViewModel::class.java]

        VM.liveData.observe(viewLifecycleOwner) {
            updateController(it)
        }

        // Toolbar
        binding.layoutToolbarAction.apply {
            txtTitle.text = args.developerName
            toolbar.setOnClickListener {
                findNavController().navigateUp()
            }
        }

        // Recycler View
        endlessRecyclerOnScrollListener = object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore(currentPage: Int) {
                VM.liveData.value?.let { VM.next(it.subBundles) }
            }
        }
        binding.recycler.addOnScrollListener(endlessRecyclerOnScrollListener)

        VM.observeSearchResults("pub:${args.developerName}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateController(searchBundle: SearchBundle) {
        binding.recycler
            .withModels {
                setFilterDuplicates(true)
                searchBundle.appList.forEach { app ->
                    add(
                        AppListViewModel_()
                            .id(app.id)
                            .app(app)
                            .click(View.OnClickListener {
                                openDetailsFragment(app)
                            })
                    )
                }

                if (searchBundle.subBundles.isNotEmpty()) {
                    add(
                        AppProgressViewModel_()
                            .id("progress")
                    )
                }
            }
    }
}
