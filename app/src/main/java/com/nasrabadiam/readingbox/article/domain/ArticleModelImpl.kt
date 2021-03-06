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

package com.nasrabadiam.readingbox.article.domain

import com.nasrabadiam.readingbox.data.Repository

class ArticleModelImpl(private val repo: Repository) : ArticleModel {
    override fun getAll(): List<Article> {
        return repo.articleRepo.getAll()
    }

    override fun get(id: Int): Article {
        return repo.articleRepo.get(id)
    }

    override fun addArticle(link: String, callback: CallBack<Article>) {
        repo.articleRepo.addArticle(link, callback)
    }

    override fun remove(id: Int, callback: CallBack<Int>) {
        repo.articleRepo.remove(id,callback)
    }

}
