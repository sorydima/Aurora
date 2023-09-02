package com.aurora.store.view.ui.commons

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aurora.store.R
import com.aurora.store.data.model.Black
import com.aurora.store.data.providers.BlacklistProvider
import com.aurora.store.databinding.ActivityGenericRecyclerBinding
import com.aurora.store.view.epoxy.views.BlackListViewModel_
import com.aurora.store.view.epoxy.views.shimmer.AppListViewShimmerModel_
import com.aurora.store.viewmodel.all.BlacklistViewModel


class BlacklistFragment : Fragment(R.layout.activity_generic_recycler) {

    private var _binding: ActivityGenericRecyclerBinding? = null
    private val binding: ActivityGenericRecyclerBinding
        get() = _binding!!

    private lateinit var VM: BlacklistViewModel
    private lateinit var blacklistProvider: BlacklistProvider

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = ActivityGenericRecyclerBinding.bind(view)
        VM = ViewModelProvider(this)[BlacklistViewModel::class.java]
        blacklistProvider = BlacklistProvider.with(view.context)

        VM.liveData.observe(viewLifecycleOwner) {
            updateController(it.sortedByDescending { app ->
                blacklistProvider.isBlacklisted(app.packageName)
            })
        }

        // Toolbar
        binding.layoutToolbarAction.txtTitle.text = getString(R.string.title_blacklist_manager)
        binding.layoutToolbarAction.imgActionPrimary.setOnClickListener {
            findNavController().navigateUp()
        }

        updateController(null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        blacklistProvider.save(VM.selected)
    }

    private fun updateController(blackList: List<Black>?) {
        binding.recycler.withModels {
            setFilterDuplicates(true)
            if (blackList == null) {
                for (i in 1..6) {
                    add(
                        AppListViewShimmerModel_()
                            .id(i)
                    )
                }
            } else {
                blackList.forEach {
                    add(
                        BlackListViewModel_()
                            .id(it.packageName.hashCode())
                            .black(it)
                            .markChecked(VM.selected.contains(it.packageName))
                            .checked { _, isChecked ->
                                if (isChecked)
                                    VM.selected.add(it.packageName)
                                else
                                    VM.selected.remove(it.packageName)

                                requestModelBuild()
                            }
                    )
                }
            }
        }
    }
}
