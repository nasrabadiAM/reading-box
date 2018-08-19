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
import com.nasrabadiam.readingbox.data.db.AppDatabase
import com.nasrabadiam.readingbox.data.db.article.ArticleEntity

class ArticleRepoImpl(private val appDatabase: AppDatabase) : ArticleRepo {


    override fun getAll(): List<Article> {
        return appDatabase.articleDao()
                .getAllArticles().map { ArticleConverter.getDomainVersion(it) }
    }

    override fun get(id: Int): Article {
        return ArticleConverter.getDomainVersion(
                appDatabase.articleDao().getArticle(id))
    }

    override fun addArticle(link: String): Boolean {
        appDatabase.articleDao().addArticle(ArticleEntity(link = link))
        return true
    }

}