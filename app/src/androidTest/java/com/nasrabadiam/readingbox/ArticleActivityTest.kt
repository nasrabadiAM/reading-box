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

package com.nasrabadiam.readingbox

import android.content.Context
import android.webkit.WebView
import org.hamcrest.core.IsEqual
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowWebView


@RunWith(RobolectricTestRunner::class)
class ArticleActivityTest {

    val SAMPLE_URL = "http://www.google.com"

    fun context(): Context {
        return RuntimeEnvironment.application
    }

    private var webView: WebView? = null
    private var shadowWebView: ShadowWebView? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        webView = WebView(RuntimeEnvironment.application)
        shadowWebView = Shadows.shadowOf(webView)
    }

    @Test
    fun shouldRecordLastLoadedUrl() {
        webView!!.loadUrl(SAMPLE_URL)
        Assert.assertTrue(shadowOf(webView).lastLoadedUrl == SAMPLE_URL)
    }

    @Test
    fun shouldRecordLastLoadedUrlForRequestWithAdditionalHeaders() {
        webView!!.loadUrl(SAMPLE_URL, null)
        assertThat(shadowOf(webView).lastLoadedUrl, IsEqual(SAMPLE_URL))
        assertTrue(shadowOf(webView).lastAdditionalHttpHeaders.isEmpty())
        val additionalHttpHeaders = HashMap<String, String>(1)
        additionalHttpHeaders["key1"] = "value1"
        webView!!.loadUrl(SAMPLE_URL, additionalHttpHeaders)
        assertTrue(shadowOf(webView).lastLoadedUrl == SAMPLE_URL)
        assertTrue(shadowOf(webView).lastAdditionalHttpHeaders.isNotEmpty())
        assertTrue(shadowOf(webView).lastAdditionalHttpHeaders.containsKey("key1"))
        assertThat(shadowOf(webView).lastAdditionalHttpHeaders["key1"], IsEqual("value1"))
    }

    /* @Test
     fun shouldRecordLastLoadedData() {
         webView!!.loadData("<html><body><h1>Hi</h1></body></html>", "text/html", "utf-8")
         val lastLoadData = shadowOf(webView).getLastLoadData()
         assertThat(lastLoadData.data).isEqualTo("<html><body><h1>Hi</h1></body></html>")
         assertThat(lastLoadData.mimeType).isEqualTo("text/html")
         assertThat(lastLoadData.encoding).isEqualTo("utf-8")
     }

     @Test
     @Throws(Exception::class)
     fun shouldRecordLastLoadDataWithBaseURL() {
         webView!!.loadDataWithBaseURL("base/url", "<html><body><h1>Hi</h1></body></html>", "text/html", "utf-8", "history/url")
         val lastLoadData = shadowOf(webView).getLastLoadDataWithBaseURL()
         assertThat(lastLoadData.baseUrl).isEqualTo("base/url")
         assertThat(lastLoadData.data).isEqualTo("<html><body><h1>Hi</h1></body></html>")
         assertThat(lastLoadData.mimeType).isEqualTo("text/html")
         assertThat(lastLoadData.encoding).isEqualTo("utf-8")
         assertThat(lastLoadData.historyUrl).isEqualTo("history/url")
     }

     @Test
     fun shouldReturnSettings() {
         val webSettings = webView!!.settings

         assertThat(webSettings).isNotNull()
     }

     @Test
     fun shouldRecordWebViewClient() {
         val webViewClient = WebViewClient()

         assertThat(shadowWebView!!.webViewClient).isNull()
         webView!!.webViewClient = webViewClient
         assertThat(shadowWebView!!.webViewClient).isSameAs(webViewClient)
     }

     @Test
     fun shouldRecordWebChromeClient() {
         val webChromeClient = WebChromeClient()
         assertThat(shadowWebView!!.webChromeClient).isNull()
         webView!!.webChromeClient = webChromeClient
         assertThat(shadowWebView!!.webChromeClient).isSameAs(webChromeClient)
     }

     @Test
     fun shouldRecordJavascriptInteraces() {
         val names = arrayOf("name1", "name2")
         for (name in names) {
             val obj = Any()
             assertThat(shadowWebView!!.getJavascriptInterface(name)).isNull()
             webView!!.addJavascriptInterface(obj, name)
             assertThat(shadowWebView!!.getJavascriptInterface(name)).isSameAs(obj)
         }
     }

     @Test
     @Throws(Exception::class)
     fun canGoBack() {
         shadowWebView!!.clearHistory()
         assertThat(webView!!.canGoBack()).isFalse()
         shadowWebView!!.loadUrl("fake.url", null)
         shadowWebView!!.loadUrl("fake.url", null)
         assertThat(webView!!.canGoBack()).isTrue()
         webView!!.goBack()
         assertThat(webView!!.canGoBack()).isFalse()
     }

     @Test
     @Throws(Exception::class)
     fun shouldStoreTheNumberOfTimesGoBackWasCalled() {
         assertThat(shadowWebView!!.goBackInvocations).isEqualTo(0)
         webView!!.goBack()
         webView!!.loadUrl("foo.bar", null)
         // If there is no history (only one page), we shouldn't invoke go back.
         assertThat(shadowWebView!!.goBackInvocations).isEqualTo(0)
         webView!!.loadUrl("foo.bar", null)
         webView!!.loadUrl("foo.bar", null)
         webView!!.loadUrl("foo.bar", null)
         webView!!.loadUrl("foo.bar", null)
         webView!!.loadUrl("foo.bar", null)
         webView!!.goBack()
         assertThat(shadowWebView!!.goBackInvocations).isEqualTo(1)
         webView!!.goBack()
         webView!!.goBack()
         assertThat(shadowWebView!!.goBackInvocations).isEqualTo(3)
         webView!!.goBack()
         webView!!.goBack()
         webView!!.goBack()
         // We've gone back one too many times for the history, so we should only have 5 invocations.
         assertThat(shadowWebView!!.goBackInvocations).isEqualTo(5)
     }

     @Test
     fun shouldStoreTheNumberOfTimesGoBackWasCalled_SetCanGoBack() {
         shadowWebView!!.setCanGoBack(true)
         webView!!.goBack()
         webView!!.goBack()
         assertThat(shadowWebView!!.goBackInvocations).isEqualTo(2)
         shadowWebView!!.setCanGoBack(false)
         webView!!.goBack()
         webView!!.goBack()
         assertThat(shadowWebView!!.goBackInvocations).isEqualTo(2)
     }

     @Test
     fun shouldRecordClearCacheWithoutDiskFiles() {
         assertThat(shadowWebView!!.wasClearCacheCalled()).isFalse()

         webView!!.clearCache(false)
         assertThat(shadowWebView!!.wasClearCacheCalled()).isTrue()
         assertThat(shadowWebView!!.didClearCacheIncludeDiskFiles()).isFalse()
     }

     @Test
     fun shouldRecordClearCacheWithDiskFiles() {
         assertThat(shadowWebView!!.wasClearCacheCalled()).isFalse()

         webView!!.clearCache(true)
         assertThat(shadowWebView!!.wasClearCacheCalled()).isTrue()
         assertThat(shadowWebView!!.didClearCacheIncludeDiskFiles()).isTrue()
     }

     @Test
     fun shouldRecordClearFormData() {
         assertThat(shadowWebView!!.wasClearFormDataCalled()).isFalse()
         webView!!.clearFormData()
         assertThat(shadowWebView!!.wasClearFormDataCalled()).isTrue()
     }

     @Test
     fun shouldRecordClearHistory() {
         assertThat(shadowWebView!!.wasClearHistoryCalled()).isFalse()
         webView!!.clearHistory()
         assertThat(shadowWebView!!.wasClearHistoryCalled()).isTrue()
     }

     @Test
     fun shouldRecordClearView() {
         assertThat(shadowWebView!!.wasClearViewCalled()).isFalse()
         webView!!.clearView()
         assertThat(shadowWebView!!.wasClearViewCalled()).isTrue()
     }

     @Test
     @Throws(Exception::class)
     fun getOriginalUrl() {
         webView!!.clearHistory()
         assertThat(webView!!.originalUrl).isNull()
         webView!!.loadUrl("fake.url", null)
         assertThat(webView!!.originalUrl).isEqualTo("fake.url")
     }

     @Test
     @Throws(Exception::class)
     fun getUrl() {
         webView!!.clearHistory()
         assertThat(webView!!.url).isNull()
         webView!!.loadUrl("fake.url", null)
         assertThat(webView!!.url).isEqualTo("fake.url")
     }

     @Test
     @Config(minSdk = 19)
     fun evaluateJavascript() {
         assertThat(shadowWebView!!.lastEvaluatedJavascript).isNull()
         webView!!.evaluateJavascript("myScript", null)
         assertThat(shadowWebView!!.lastEvaluatedJavascript).isEqualTo("myScript")
     }

     @Test
     fun shouldRecordDestroy() {
         assertThat(shadowWebView!!.wasDestroyCalled()).isFalse()
         webView!!.destroy()
         assertThat(shadowWebView!!.wasDestroyCalled()).isTrue()
     }

     @Test
     fun shouldRecordOnPause() {
         assertThat(shadowWebView!!.wasOnPauseCalled()).isFalse()
         webView!!.onPause()
         assertThat(shadowWebView!!.wasOnPauseCalled()).isTrue()
     }

     @Test
     fun shouldRecordOnResume() {
         assertThat(shadowWebView!!.wasOnResumeCalled()).isFalse()
         webView!!.onResume()
         assertThat(shadowWebView!!.wasOnResumeCalled()).isTrue()
     }

     @Test
     fun shouldReturnPreviouslySetLayoutParams() {
         assertThat(webView!!.layoutParams).isNull()
         val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
         webView!!.layoutParams = params
         assertThat(webView!!.layoutParams).isSameAs(params)
     }
 */
}