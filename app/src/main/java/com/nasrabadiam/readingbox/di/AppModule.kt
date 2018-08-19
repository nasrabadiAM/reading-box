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
import android.content.SharedPreferences
import com.nasrabadiam.readingbox.ReadingBoxApplication
import com.nasrabadiam.readingbox.article.articleList.ArticleListContract
import com.nasrabadiam.readingbox.article.articleList.ArticleListPresenter
import com.nasrabadiam.readingbox.article.domain.ArticleModel
import com.nasrabadiam.readingbox.article.domain.ArticleModelImpl
import com.nasrabadiam.readingbox.data.LocalDataSource
import com.nasrabadiam.readingbox.data.RemoteDataSource
import com.nasrabadiam.readingbox.data.Repository
import com.nasrabadiam.readingbox.data.db.AppDatabase
import com.nasrabadiam.readingbox.data.network.ReadingBoxRemoteDataServiceProvider
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
    internal fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.applicationContext.getSharedPreferences("default_pref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    internal fun provideLocalDataSource(appDatabase: AppDatabase, sharedPreferences: SharedPreferences): LocalDataSource {
        return LocalDataSource(appDatabase, sharedPreferences)
    }

    @Provides
    @Singleton
    internal fun provideReadingBoxRemoteDataServiceProvider(): ReadingBoxRemoteDataServiceProvider {
        return ReadingBoxRemoteDataServiceProvider()
    }

    @Provides
    @Singleton
    internal fun provideRemoteDataSource(readingBoxRemoteDataServiceProvider: ReadingBoxRemoteDataServiceProvider): RemoteDataSource {
        return RemoteDataSource(readingBoxRemoteDataServiceProvider)
    }

    @Provides
    @Singleton
    internal fun provideRepository(localDataSource: LocalDataSource,
                                   remoteDataSource: RemoteDataSource): Repository {
        return Repository(localDataSource, remoteDataSource)
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