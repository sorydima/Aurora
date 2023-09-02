package com.aurora.store.view.ui.commons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.aurora.Constants
import com.aurora.gplayapi.data.models.Category
import com.aurora.store.R
import com.aurora.store.databinding.FragmentGenericRecyclerBinding
import com.aurora.store.view.epoxy.views.CategoryViewModel_
import com.aurora.store.viewmodel.category.AppCategoryViewModel
import com.aurora.store.viewmodel.category.BaseCategoryViewModel
import com.aurora.store.viewmodel.category.GameCategoryViewModel


class CategoryFragment : BaseFragment() {

    private lateinit var B: FragmentGenericRecyclerBinding
    private lateinit var VM: BaseCategoryViewModel

    private var pageType = 0

    companion object {
        @JvmStatic
        fun newInstance(pageType: Int): CategoryFragment {
            return CategoryFragment().apply {
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
        B = FragmentGenericRecyclerBinding.bind(
            inflater.inflate(
                R.layout.fragment_generic_recycler,
                container,
                false
            )
        )

        val bundle = arguments
        if (bundle != null) {
            pageType = bundle.getInt(Constants.PAGE_TYPE, 0)
        }

        when (pageType) {
            0 -> VM = ViewModelProvider(this)[AppCategoryViewModel::class.java]
            1 -> VM = ViewModelProvider(this)[GameCategoryViewModel::class.java]
        }

        return B.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        VM.liveData.observe(viewLifecycleOwner) {
            updateController(it)
        }
    }

    private fun updateController(categoryList: List<Category>) {
        B.recycler.withModels {
            setFilterDuplicates(true)
            categoryList.forEach {
                add(
                    CategoryViewModel_()
                        .id(it.title)
                        .category(it)
                        .click { _ -> openCategoryBrowseFragment(it) }
                )
            }
        }
    }
}
