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

package com.aurora.store.util

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.io.IOException

object Log {

    const val TAG = "¯\\_(ツ)_/¯ "

    fun e(message: String?, vararg args: Any?) {
        e(String.format(message!!, *args))
    }

    fun e(message: String?) {
        Log.e(TAG, message!!)
    }

    fun i(message: String?, vararg args: Any?) {
        i(String.format(message!!, *args))
    }

    fun i(message: String?) {
        Log.i(TAG, message!!)
    }

    fun d(message: String?, vararg args: Any?) {
        d(String.format(message!!, *args))
    }

    fun d(message: String?) {
        Log.d(TAG, message!!)
    }

    fun w(message: String?, vararg args: Any?) {
        w(String.format(message!!, *args))
    }

    fun w(message: String?) {
        Log.w(TAG, message!!)
    }

    fun writeToFile(context: Context, obj: Any) {
        try {
            val out = FileWriter(File(context.filesDir, "AuroraLogs.txt"))
            out.write(obj.toString())
            out.close()
        } catch (e: IOException) {
            e(e.message)
        }
    }
}