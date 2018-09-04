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

data class ServiceArticle(
        @SerializedName("title")
        val title: String,
        @SerializedName("summary")
        val summary: String,
        @SerializedName("body")
        val body: String,
        @SerializedName("top_image")
        val leadImageUrl: String,
        @SerializedName("authors")
        val authors: List<String>,
        @SerializedName("publish_date")
        val publish_date: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("source_url")
        val source_url: String,
        @SerializedName("words_count")
        val word_count: Int,
        @SerializedName("keywords")
        val keywords: List<String>,
        @SerializedName("tags")
        val tags: List<String>,
        @SerializedName("lang")
        val lang: String
)
