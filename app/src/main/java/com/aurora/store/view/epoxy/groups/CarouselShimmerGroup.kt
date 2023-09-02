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
import com.aurora.store.R
import com.aurora.store.view.epoxy.views.shimmer.AppViewShimmerModel_
import com.aurora.store.view.epoxy.views.shimmer.HeaderViewShimmerModel_
import java.util.UUID

class CarouselShimmerGroup() :
    EpoxyModelGroup(
        R.layout.model_carousel_group, buildModels()
    ) {
    companion object {
        private fun buildModels(): List<EpoxyModel<*>> {
            val models = ArrayList<EpoxyModel<*>>()
            val clusterViewModels = mutableListOf<EpoxyModel<*>>()
            val idPrefix = UUID.randomUUID()

            for (i in 1..8) {
                clusterViewModels.add(
                    AppViewShimmerModel_()
                        .id(i)
                )
            }

            models.add(
                HeaderViewShimmerModel_()
                    .id("shimmer_header")
            )

            models.add(
                CarouselHorizontalModel_()
                    .id("cluster_${idPrefix}")
                    .models(clusterViewModels)
            )
            return models
        }
    }
}
