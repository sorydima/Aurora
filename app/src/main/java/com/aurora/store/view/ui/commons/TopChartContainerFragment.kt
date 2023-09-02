package com.aurora.store.view.ui.commons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.aurora.Constants
import com.aurora.gplayapi.data.models.AuthData
import com.aurora.store.R
import com.aurora.store.data.providers.AuthProvider
import com.aurora.store.databinding.FragmentTopChartBinding
import com.aurora.store.view.ui.games.TopChartFragment


class TopChartContainerFragment : Fragment() {

    private lateinit var B: FragmentTopChartBinding

    private lateinit var authData: AuthData

    private var chartType = 0

    companion object {
        @JvmStatic
        fun newInstance(chartType: Int): TopChartContainerFragment {
            return TopChartContainerFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constants.TOP_CHART_TYPE, chartType)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        B = FragmentTopChartBinding.bind(
            inflater.inflate(
                R.layout.fragment_top_chart,
                container,
                false
            )
        )

        val bundle = arguments
        if (bundle != null) {
            chartType = bundle.getInt(Constants.TOP_CHART_TYPE, 0)
        }

        return B.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authData = AuthProvider.with(requireContext()).getAuthData()
        setupViewPager()
    }

    private fun setupViewPager() {
        B.pager.adapter = ViewPagerAdapter(childFragmentManager, lifecycle, chartType)
        B.topTabGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            when (checkedIds[0]) {
                R.id.tab_top_free -> B.pager.setCurrentItem(0, true)
                R.id.tab_top_grossing -> B.pager.setCurrentItem(1, true)
                R.id.tab_trending -> B.pager.setCurrentItem(2, true)
                R.id.tab_top_paid -> B.pager.setCurrentItem(3, true)
            }
        }

        B.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> B.topTabGroup.check(R.id.tab_top_free)
                    1 -> B.topTabGroup.check(R.id.tab_top_grossing)
                    2 -> B.topTabGroup.check(R.id.tab_trending)
                    3 -> B.topTabGroup.check(R.id.tab_top_paid)
                }
            }
        })
    }

    internal class ViewPagerAdapter(
        fragment: FragmentManager,
        lifecycle: Lifecycle,
        chartType: Int
    ) :
        FragmentStateAdapter(fragment, lifecycle) {
        private val tabFragments: MutableList<TopChartFragment> = mutableListOf(
            TopChartFragment.newInstance(chartType, 0),
            TopChartFragment.newInstance(chartType, 1),
            TopChartFragment.newInstance(chartType, 2),
            TopChartFragment.newInstance(chartType, 3)
        )

        override fun createFragment(position: Int): Fragment {
            return tabFragments[position]
        }

        override fun getItemCount(): Int {
            return tabFragments.size
        }
    }
}
