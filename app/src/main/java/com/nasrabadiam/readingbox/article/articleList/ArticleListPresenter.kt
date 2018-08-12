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

package com.nasrabadiam.readingbox.article.articleList

import com.nasrabadiam.readingbox.article.Article

class ArticleListPresenter : ArticleListContract.Presenter {

    private lateinit var view: ArticleListContract.View

    override fun setView(view: ArticleListContract.View) {
        this.view = view
    }

    override fun getAllArticles() {
        val item1 = Article(1, "title", link = "https://www.google.com",
                guid = "https://www.google.com")
        view.showArticles(listOf(item1))
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}