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
import okhttp3.*


class FakeMercuryInterceptor(val context: Context) : Interceptor {

    private val fileSuffix = ".json"
    private val contentType = "application/json"

    override fun intercept(chain: Interceptor.Chain?): Response {

        val fileName = getFileName(chain!!)
        val fileContentString = getFileContent(fileName)

        //Handle NetworkConnectionInterceptor
        val response = chain.proceed(chain.request())
        if (response.code() == 420) {
            return chain.proceed(chain.request())
        }

        return Response.Builder()
                .code(200)
                .message(fileContentString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse(contentType), fileContentString.toByteArray()))
                .addHeader("content-type", contentType)
                .build()
    }


    private fun getFileName(chain: Interceptor.Chain): String {
        val fileName = chain.request().url().pathSegments()[chain.request().url().pathSegments().size - 1]
        return if (fileName.isEmpty()) "index$fileSuffix" else fileName + fileSuffix
    }

    private fun getFileContent(fileName: String): String {
        val resultInputStream = context.assets.open(fileName)

        val lineList = mutableListOf<String>()

        resultInputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it) } }
        val resultStringBuilder = StringBuilder()
        lineList.forEach { resultStringBuilder.append(it) }
        return resultStringBuilder.toString()
    }
}