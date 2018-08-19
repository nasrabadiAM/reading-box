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

import okhttp3.Interceptor
import okhttp3.Response


class MercuryInterceptor : Interceptor {


    val API_KEY = "jtePsEzEBi6u6zsPC4S2RSXUiaEtqcEGlbEW4P6D"

    override fun intercept(chain: Interceptor.Chain?): Response {
        return handleIntercept(chain!!, API_KEY)
    }

    companion object {

        fun handleIntercept(chain: Interceptor.Chain, apiKey: String): Response {
            val original = chain.request()

            val request = original.newBuilder()
                    .header("content-type", "application/json")
                    .header("x-api-key", apiKey)
                    .method(original.method(), original.body())
                    .build()

            return chain.proceed(request)
        }
    }

}