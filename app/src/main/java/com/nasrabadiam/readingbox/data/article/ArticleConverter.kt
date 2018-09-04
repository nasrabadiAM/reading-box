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

package com.nasrabadiam.readingbox.data.article

import com.nasrabadiam.readingbox.article.domain.Article
import com.nasrabadiam.readingbox.data.db.article.ArticleEntity
import com.nasrabadiam.readingbox.data.network.ServiceArticle
import java.text.SimpleDateFormat
import java.util.*

class ArticleConverter {

    companion object {

        fun getDomainFromDb(a: ArticleEntity): Article {
            return Article(id = a.id, title = a.title ?: "", link = a.link,
                    description = a.description ?: "", author = a.author ?: "",
                    category = a.category ?: "", comments = a.comments ?: "",
                    baseImageUrl = a.baseImageUrl ?: "", guid = a.guid,
                    pubDate = a.pubDate ?: Date(), source = a.source ?: "")
        }

        fun getDbFromDomain(a: Article): ArticleEntity {
            return ArticleEntity(id = a.id, title = a.title, link = a.link,
                    description = a.description, author = a.author,
                    category = a.category, comments = a.comments,
                    baseImageUrl = a.baseImageUrl, guid = a.guid,
                    pubDate = a.pubDate, source = a.source)
        }

        fun getDbFromService(a: ServiceArticle): ArticleEntity {
            val direction = 0 //0 is ltr

            val dateFormat = SimpleDateFormat("yyyy-dd-MM hh:mm:ss", Locale("en"))
            val pubDate = dateFormat.parse(a.publish_date)

            return ArticleEntity(title = a.title, link = a.url,
                    description = a.summary, conents = a.body,
                    baseImageUrl = a.leadImageUrl, guid = a.url,
                    pubDate = pubDate, source = a.source_url,
                    layoutDirection = direction, wordCount = a.word_count)

        }

    }
}