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
import com.nasrabadiam.readingbox.article.domain.Enclosure
import com.nasrabadiam.readingbox.data.db.article.ArticleEntity
import com.nasrabadiam.readingbox.data.db.article.EnclosureEntity

class ArticleConverter {

    companion object {

        fun getDomainVersion(a: ArticleEntity): Article {
            return Article(id = a.id, title = a.title, link = a.link,
                    description = a.description, author = a.author,
                    category = a.category, comments = a.comments,
                    enclosure = getDomainEnclosure(a.enclosure), guid = a.guid,
                    pubDate = a.pubDate, source = a.source)
        }

        fun getDbVersion(a: Article): ArticleEntity {
            return ArticleEntity(id = a.id, title = a.title, link = a.link,
                    description = a.description, author = a.author,
                    category = a.category, comments = a.comments,
                    enclosure = getDbEnclosure(a.enclosure), guid = a.guid,
                    pubDate = a.pubDate, source = a.source)
        }

        fun getDomainEnclosure(a: EnclosureEntity): Enclosure {
            return Enclosure(url = a.url, type = a.type)
        }

        fun getDbEnclosure(a: Enclosure): EnclosureEntity {
            return EnclosureEntity(url = a.url, type = a.type)
        }
    }
}