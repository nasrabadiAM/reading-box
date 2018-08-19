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

package com.nasrabadiam.readingbox.data.network

import com.google.gson.annotations.SerializedName
import java.util.*

data class MercuryArticle(
        @SerializedName("title")
        val title: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("date_published")
        val pubDate: Date,
        @SerializedName("lead_image_url")
        val leadImageUrl: String,
        @SerializedName("dek")
        val dek: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("domain")
        val domain: String,
        @SerializedName("excerpt")
        val excerpt: String,
        @SerializedName("word_count")
        val word_count: Int,
        @SerializedName("direction")
        val direction: String,
        @SerializedName("total_pages")
        val total_pages: String,
        @SerializedName("rendered_pages")
        val rendered_pages: String,
        @SerializedName("next_page_url")
        val next_page_url: String

)
