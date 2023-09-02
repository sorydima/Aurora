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

package com.aurora.store.data.providers

import android.content.Context
import com.aurora.store.data.Filter
import com.aurora.store.data.SingletonHolder
import com.aurora.store.util.Preferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Modifier

class FilterProvider private constructor(var context: Context) {

    companion object : SingletonHolder<FilterProvider, Context>(::FilterProvider) {
        const val PREFERENCE_FILTER = "PREFERENCE_FILTER"
    }

    private var gson: Gson = GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.TRANSIENT)
        .create()

    fun getSavedFilter(): Filter {
        var rawFilter = Preferences.getString(context, PREFERENCE_FILTER)
        if (rawFilter.isEmpty())
            rawFilter = "{}"
        return gson.fromJson(rawFilter, Filter::class.java)
    }

    fun saveFilter(filter: Filter) {
        Preferences.putString(context, PREFERENCE_FILTER, gson.toJson(filter))
    }
}