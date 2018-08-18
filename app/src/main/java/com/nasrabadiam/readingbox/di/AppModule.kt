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

package com.nasrabadiam.readingbox.di

import android.content.Context
import com.nasrabadiam.readingbox.ReadingBoxApplication
import com.nasrabadiam.readingbox.article.articleList.ArticleListContract
import com.nasrabadiam.readingbox.article.articleList.ArticleListPresenter
import com.nasrabadiam.readingbox.article.domain.ArticleModel
import com.nasrabadiam.readingbox.article.domain.ArticleModelImpl
import com.nasrabadiam.readingbox.data.Repository
import com.nasrabadiam.readingbox.data.article.ArticleRepo
import com.nasrabadiam.readingbox.data.article.ArticleRepoImpl
import com.nasrabadiam.readingbox.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: ReadingBoxApplication) {

    @Provides
    internal fun provideApplicationContext(): Context {
        return this.application.applicationContext
    }

    @Provides
    @Singleton
    internal fun provideAppDatabse(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }


    @Provides
    @Singleton
    internal fun provideArticleRepo(appDatabase: AppDatabase): ArticleRepo {
        return ArticleRepoImpl(appDatabase)
    }

    @Provides
    @Singleton
    internal fun provideRepository(articleRepo: ArticleRepo): Repository {
        return Repository(articleRepo)
    }

    @Provides
    internal fun provideArticleModel(repository: Repository): ArticleModel {
        return ArticleModelImpl(repository)
    }

    @Provides
    internal fun provideArticleListPresenter(articleModel: ArticleModel): ArticleListContract.Presenter {
        return ArticleListPresenter(articleModel)
    }

}