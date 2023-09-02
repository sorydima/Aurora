package com.aurora.store.view.ui.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aurora.store.R
import com.aurora.store.data.Filter
import com.aurora.store.data.providers.FilterProvider
import com.aurora.store.databinding.SheetFilterBinding
import com.google.android.material.chip.Chip

class FilterSheet : BaseBottomSheet() {

    private lateinit var B: SheetFilterBinding
    private lateinit var filter: Filter

    override fun onCreateContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?
    ): View {
        B = SheetFilterBinding.inflate(inflater, container, false)
        filter = FilterProvider.with(requireContext()).getSavedFilter()
        return B.root
    }

    override fun onContentViewCreated(view: View, savedInstanceState: Bundle?) {
        attachSingleChips()
        attachMultipleChips()
        attachActions()
    }

    private fun attachActions() {
        B.btnPositive.setOnClickListener {
            FilterProvider.with(requireContext()).saveFilter(filter)
            dismissAllowingStateLoss()
        }

        B.btnNegative.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }

    private fun attachSingleChips() {
        B.filterGfs.apply {
            isChecked = filter.gsfDependentApps
            setOnCheckedChangeListener { _, checked ->
                isChecked = checked
                filter.gsfDependentApps = checked
            }
        }

        B.filterPaid.apply {
            isChecked = filter.paidApps
            setOnCheckedChangeListener { _, checked ->
                isChecked = checked
                filter.paidApps = checked
            }
        }

        B.filterAds.apply {
            isChecked = filter.appsWithAds
            setOnCheckedChangeListener { _, checked ->
                isChecked = checked
                filter.appsWithAds = checked
            }
        }
    }

    private fun attachMultipleChips() {
        val downloadLabels = resources.getStringArray(R.array.filterDownloadsLabels)
        val downloadValues = resources.getStringArray(R.array.filterDownloadsValues)
        val ratingLabels = resources.getStringArray(R.array.filterRatingLabels)
        val ratingValues = resources.getStringArray(R.array.filterRatingValues)
        var i = 0

        for (downloadLabel in downloadLabels) {
            val chip = Chip(requireContext())
            chip.id = i
            chip.text = downloadLabel
            chip.isChecked = filter.downloads == downloadValues[i].toInt()
            B.downloadChips.addView(chip)
            i++
        }

        B.downloadChips.setOnCheckedStateChangeListener { _, checkedIds ->
            filter.downloads = downloadValues[checkedIds[0]].toInt()
        }

        i = 0
        for (ratingLabel in ratingLabels) {
            val chip = Chip(requireContext())
            chip.id = i
            chip.text = ratingLabel
            chip.isChecked = filter.rating == ratingValues[i].toFloat()
            B.ratingChips.addView(chip)
            i++
        }

        B.ratingChips.setOnCheckedStateChangeListener { _, checkedIds ->
            filter.rating = ratingValues[checkedIds[0]].toFloat()
        }
    }
}
