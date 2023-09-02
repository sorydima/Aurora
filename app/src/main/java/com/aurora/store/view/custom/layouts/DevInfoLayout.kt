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

package com.aurora.store.view.custom.layouts

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.aurora.store.R
import com.aurora.store.databinding.ViewDevInfoBinding

class DevInfoLayout : RelativeLayout {

    private lateinit var B: ViewDevInfoBinding

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val view = inflate(context, R.layout.view_dev_info, this)
        B = ViewDevInfoBinding.bind(view)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DevInfoLayout)
        val icon = typedArray.getResourceId(
            R.styleable.DevInfoLayout_imgIcon,
            R.drawable.ic_map_marker
        )

        val textPrimary = typedArray.getString(R.styleable.DevInfoLayout_txtTitle)
        val textSecondary = typedArray.getString(R.styleable.DevInfoLayout_txtSubtitle)

        B.img.setImageResource(icon)
        B.txtTitle.text = textPrimary
        B.txtSubtitle.text = textSecondary
        typedArray.recycle()
    }

    fun setTxtSubtitle(text: String?) {
        B.txtSubtitle.text = text
        invalidate()
    }
}
