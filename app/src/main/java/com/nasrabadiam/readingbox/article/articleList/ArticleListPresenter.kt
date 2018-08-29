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

import com.nasrabadiam.readingbox.article.ArticleConverter
import com.nasrabadiam.readingbox.article.domain.Article
import com.nasrabadiam.readingbox.article.domain.ArticleModel
import com.nasrabadiam.readingbox.article.domain.CallBack
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class ArticleListPresenter(val article: ArticleModel) : ArticleListContract.Presenter {
    private lateinit var view: ArticleListContract.View

    override fun setView(view: ArticleListContract.View) {
        this.view = view
    }

    override fun getAllArticles() {
        launch {
            val articles = article.getAll().map {
                ArticleConverter.getViewVersion(it)
            }
            view.showArticles(articles)
        }
    }

    override fun addArticle(link: String) {
        launch {
            article.addArticle(link, object : CallBack<Article> {
                override fun onSuccess(response: Article) {
                    view.addNewArticleToItems(ArticleConverter.getViewVersion(response))
                    view.articleAddedSuccessfully()
                }

                override fun onFail() {
                    view.articleAddFailed()
                }
            })
        }
    }

    override fun removeArticle(id: Int) {
        launch {
            article.remove(id, object : CallBack<Int> {
                override fun onSuccess(response: Int) {
                    launch(UI) {
                        view.removeArticleFromItems(response)
                        view.articleRemoveSuccessfully()
                    }
                }

                override fun onFail() {
                    launch(UI) { view.articleRemoveFailed() }
                }
            })
        }
    }

}
