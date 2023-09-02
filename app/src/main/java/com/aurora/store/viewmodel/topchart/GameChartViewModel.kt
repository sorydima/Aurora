package com.aurora.store.viewmodel.topchart

import android.app.Application
import com.aurora.gplayapi.helpers.TopChartsHelper

class TopFreeGameChartViewModel(application: Application) : BaseChartViewModel(application) {

    init {
        type = TopChartsHelper.Type.GAME
        chart = TopChartsHelper.Chart.TOP_SELLING_FREE
        observe()
    }
}

class TopGrossingGameChartViewModel(application: Application) : BaseChartViewModel(application) {

    init {
        type = TopChartsHelper.Type.GAME
        chart = TopChartsHelper.Chart.TOP_GROSSING
        observe()
    }
}

class TrendingGameChartViewModel(application: Application) : BaseChartViewModel(application) {

    init {
        type = TopChartsHelper.Type.GAME
        chart = TopChartsHelper.Chart.MOVERS_SHAKERS
        observe()
    }
}

class TopPaidGameChartViewModel(application: Application) : BaseChartViewModel(application) {

    init {
        type = TopChartsHelper.Type.GAME
        chart = TopChartsHelper.Chart.TOP_SELLING_PAID
        observe()
    }
}

