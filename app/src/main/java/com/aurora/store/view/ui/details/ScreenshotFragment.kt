package com.aurora.store.view.ui.details

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.aurora.gplayapi.data.models.Artwork
import com.aurora.store.R
import com.aurora.store.databinding.FragmentScreenshotBinding
import com.aurora.store.view.epoxy.views.details.LargeScreenshotViewModel_

class ScreenshotFragment : Fragment(R.layout.fragment_screenshot) {

    private var _binding: FragmentScreenshotBinding? = null
    private val binding: FragmentScreenshotBinding
        get() = _binding!!

    private val args: ScreenshotFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentScreenshotBinding.bind(view)

        // Toolbar
        binding.toolbar.apply {
            elevation = 0f
            navigationIcon = ContextCompat.getDrawable(view.context, R.drawable.ic_arrow_back)
            setNavigationOnClickListener { findNavController().navigateUp() }
        }

        // Recycler View
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
            PagerSnapHelper().attachToRecyclerView(this)
        }

        updateController(args.arrayOfArtwork, args.position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateController(artworks: Array<Artwork>, position: Int) {
        binding.recyclerView.withModels {
            artworks.forEach {
                add(
                    LargeScreenshotViewModel_()
                        .id(it.url)
                        .artwork(it)
                )
            }
            binding.recyclerView.scrollToPosition(position)
        }
    }
}
