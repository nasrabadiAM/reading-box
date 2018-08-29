/*
 *     This is the source code of reading-box project.
 *     Copyright (C)   Ali Nasrabadi  2018-2018
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.nasrabadiam.readingbox

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Patterns
import android.widget.ImageView
import com.squareup.picasso.Picasso


fun ImageView.loadUrl(url: String) {
    if (url.isEmpty()) return
    val picasso = Picasso.Builder(this.context).build()
    picasso.load(url).into(this)
}


fun String.isUrlValid(): Boolean {
    val p = Patterns.WEB_URL
    val m = p.matcher(this.toLowerCase())
    return m.matches()
}