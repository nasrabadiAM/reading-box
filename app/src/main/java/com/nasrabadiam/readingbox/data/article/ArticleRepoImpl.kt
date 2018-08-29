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
import com.nasrabadiam.readingbox.article.domain.CallBack
import com.nasrabadiam.readingbox.data.LocalDataSource
import com.nasrabadiam.readingbox.data.RemoteDataSource
import com.nasrabadiam.readingbox.data.network.MercuryArticle
import kotlinx.coroutines.experimental.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepoImpl(localDataSource: LocalDataSource,
                      remoteDataSource: RemoteDataSource) : ArticleRepo {
    private val appDatabase = localDataSource.appDatabase

    private val remoteSource = remoteDataSource.readingBoxSource

    override fun getAll(): List<Article> {
        return appDatabase.articleDao()
                .getAllArticles().map { ArticleConverter.getDomainFromDb(it) }
    }

    override fun get(id: Int): Article {
        return ArticleConverter.getDomainFromDb(
                appDatabase.articleDao().getArticle(id))
    }

    override fun addArticle(link: String, callback: CallBack<Article>) {
        val call = remoteSource.article.getArticleDetails(link)
        call.enqueue(object : Callback<MercuryArticle> {
            override fun onResponse(call: Call<MercuryArticle>, response: Response<MercuryArticle>) {
                when {

                    response.isSuccessful && response.body() != null -> {
                        val articleEntity =
                                ArticleConverter.getDbFromMercury(response.body()!!)
                        launch {
                            appDatabase.articleDao().addArticle(articleEntity)
                        }
                        val article = ArticleConverter.getDomainFromDb(articleEntity)
                        callback.onSuccess(article)
                    }
                    else -> callback.onFail()
                }
            }

            override fun onFailure(call: Call<MercuryArticle>, t: Throwable) {
                callback.onFail()
            }
        })
    }

    override fun remove(id: Int, callback: CallBack<Int>) {
        val result = appDatabase.articleDao().removeArticle(id)
        if (result > 0) callback.onSuccess(id)
        else callback.onFail()
    }
}