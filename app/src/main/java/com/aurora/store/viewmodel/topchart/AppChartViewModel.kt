package com.aurora.store.viewmodel.topchart

import android.app.Application
import com.aurora.gplayapi.helpers.TopChartsHelper

class TopFreeAppChartViewModel(application: Application) : BaseChartViewModel(application) {

    init {
        type = TopChartsHelper.Type.APPLICATION
        chart = TopChartsHelper.Chart.TOP_SELLING_FREE
        observe()
    }
}

class TopGrossingAppChartViewModel(application: Application) : BaseChartViewModel(application) {

    init {
        type = TopChartsHelper.Type.APPLICATION
        chart = TopChartsHelper.Chart.TOP_GROSSING
        observe()
    }
}

class TrendingAppChartViewModel(application: Application) : BaseChartViewModel(application) {

    init {
        type = TopChartsHelper.Type.APPLICATION
        chart = TopChartsHelper.Chart.MOVERS_SHAKERS
        observe()
    }
}

class TopPaidAppChartViewModel(application: Application) : BaseChartViewModel(application) {

    init {
        type = TopChartsHelper.Type.APPLICATION
        chart = TopChartsHelper.Chart.TOP_SELLING_PAID
        observe()
    }
}

