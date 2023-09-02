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

package com.aurora.store.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

data class ExodusReport(
    val creator: String = String(),
    val name: String = String(),
    val reports: List<Report> = listOf()
)

@Parcelize
data class Report(
    val id: Int = 0,
    val downloads: String = String(),
    val version: String = String(),
    val creationDate: String = String(),
    val updatedAt: String = String(),
    val versionCode: String = String(),
    val trackers: List<Int> = listOf()
) : Parcelable {

    fun getFormattedCreationDate(): String {
        return try {
            val simpleDateFormat: DateFormat = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault()
            )
            simpleDateFormat.parse(creationDate).toString()
        } catch (e: ParseException) {
            ""
        }
    }
}

data class ExodusTracker(
    var id: Int = 0,
    var name: String = String(),
    var url: String = String(),
    var signature: String = String(),
    var date: String = String(),
    var description: String = String(),
    var networkSignature: String = String(),
    var documentation: List<String> = emptyList(),
    var categories: List<String> = emptyList()
) {

    override fun hashCode(): Int {
        return id
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is ExodusTracker -> other.id == id
            else -> false
        }
    }
}
