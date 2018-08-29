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

import android.content.Context
import com.google.gson.GsonBuilder
import com.nasrabadiam.readingbox.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReadingBoxRemoteDataServiceProvider(val context: Context) {

    private val MERCURY_BASE_URL = "https://mercury.postlight.com"

    private var mRetrofitMercuryClient: Retrofit

    var article: ArticleService

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY


        val mercuryHttpClient = OkHttpClient.Builder()

        mercuryHttpClient.addInterceptor(logging)

        //set best interceptor
        if (BuildConfig.DEBUG) {
            mercuryHttpClient.addInterceptor(FakeMercuryInterceptor(context))
        } else {
            mercuryHttpClient.addInterceptor(MercuryInterceptor())
        }

        val gsonConverterFactory =
                GsonConverterFactory.create(GsonBuilder()
                        .create())

        mRetrofitMercuryClient = Retrofit.Builder()
                .baseUrl(MERCURY_BASE_URL)
                .client(mercuryHttpClient.build())
                .addConverterFactory(gsonConverterFactory)
                .build()

        article = mRetrofitMercuryClient.create(ArticleService::class.java)
    }

}