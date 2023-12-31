/*
 * Aurora Store
 * Copyright (C) © A Dmitry Sorokin production. All rights reserved. Powered by Katya AI. 👽 Copyright © 2021-2023 Katya, Inc Katya ® is a registered trademark Sponsored by REChain. 🪐 hr@rechain.email p2p@rechain.email pr@rechain.email sorydima@rechain.email support@rechain.email sip@rechain.email Please allow anywhere from 1 to 5 business days for E-mail responses! 💌
 *
 *  Aurora Store is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  Aurora Store is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Aurora Store.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.aurora.store.view.epoxy.groups

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelGroup
import com.aurora.gplayapi.data.models.StreamCluster
import com.aurora.store.R
import com.aurora.store.util.Log
import com.aurora.store.view.epoxy.controller.GenericCarouselController
import com.aurora.store.view.epoxy.views.HeaderViewModel_
import com.aurora.store.view.epoxy.views.app.AppListViewModel_
import com.aurora.store.view.epoxy.views.app.AppViewModel_
import com.aurora.store.view.epoxy.views.details.ScreenshotViewModel_

class DeveloperModelGroup(
    streamCluster: StreamCluster,
    callbacks: GenericCarouselController.Callbacks
) :
    EpoxyModelGroup(
        R.layout.model_developer_carousel_group, buildModels(
            streamCluster,
            callbacks
        )
    ) {
    companion object {
        private fun buildModels(
            streamCluster: StreamCluster,
            callbacks: GenericCarouselController.Callbacks
        ): List<EpoxyModel<*>> {
            val models = ArrayList<EpoxyModel<*>>()
            val clusterViewModels = mutableListOf<EpoxyModel<*>>()
            val screenshotsViewModels = mutableListOf<EpoxyModel<*>>()

            val idPrefix = streamCluster.id

            models.add(
                HeaderViewModel_()
                    .id("${idPrefix}_header")
                    .title(streamCluster.clusterTitle)
                    .browseUrl(streamCluster.clusterBrowseUrl)
                    .click { _ ->
                        callbacks.onHeaderClicked(streamCluster)
                    }
            )

            if (streamCluster.clusterAppList.size == 1) {
                val app = streamCluster.clusterAppList[0]

                for (artwork in app.screenshots) {
                    screenshotsViewModels.add(
                        ScreenshotViewModel_()
                            .id(artwork.url)
                            .artwork(artwork)
                    )
                }

                clusterViewModels.add(
                    AppListViewModel_()
                        .id(app.id)
                        .app(app)
                        .click { _ ->
                            callbacks.onAppClick(app)
                        }
                )
            } else {
                for (app in streamCluster.clusterAppList) {
                    clusterViewModels.add(
                        AppViewModel_()
                            .id(app.id)
                            .app(app)
                            .click { _ ->
                                callbacks.onAppClick(app)
                            }
                            .longClick { _ ->
                                callbacks.onAppLongClick(app)
                                false
                            }
                            .onBind { _, _, position ->
                                val itemCount = clusterViewModels.count()
                                if (itemCount >= 2) {
                                    if (position == clusterViewModels.count() - 2) {
                                        callbacks.onClusterScrolled(streamCluster)
                                        Log.i("Cluster %s Scrolled", streamCluster.clusterTitle)
                                    }
                                }
                            }
                    )
                }
            }

            if (screenshotsViewModels.isNotEmpty()) {
                models.add(
                    CarouselHorizontalModel_()
                        .id("${idPrefix}_screenshots")
                        .models(screenshotsViewModels)
                )
            }

            models.add(
                CarouselHorizontalModel_()
                    .id("${idPrefix}_cluster")
                    .models(clusterViewModels)
            )

            return models
        }
    }
}