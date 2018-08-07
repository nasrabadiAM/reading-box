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

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.ProgressBar


class ArticleActivity : AppCompatActivity() {

    private lateinit var articleView: WebView
    private lateinit var progressbar: ProgressBar
    private lateinit var retryButton: ImageButton
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        articleView = findViewById(R.id.web_view)
        progressbar = findViewById(R.id.progressbar)
        retryButton = findViewById(R.id.failed_retry)

        url = intent.extras.getString(URL_INPUT_KEY)

        initialize(url)
    }

    private fun initialize(url: String) {

        @SuppressLint("SetJavaScriptEnabled")
        articleView.settings.javaScriptEnabled = true

        articleView.loadUrl(url)

        val webViewClient: WebViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return if (url != null &&
                        (url.startsWith("http://")
                                || url.startsWith("https://"))) {
                    view?.context?.startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    true
                } else {
                    false
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                hideLoading()
                hideRetryButton()

                supportActionBar?.title = view?.title
                showArticleView()
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                hideRetryButton()
                hideArticleView()
                showLoading()
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                view?.loadUrl("about:blank")
                hideArticleView()
                hideLoading()
                showRetryButton()
                super.onReceivedError(view, request, error)
            }
        }

        articleView.webViewClient = webViewClient
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.article_view, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                refreshArticleView(url)
                true
            }
            R.id.bookmark -> {
                //TODO: bookmark this article
                true
            }
            else -> false
        }
    }


    fun refreshArticleView(url: String) {
        articleView.loadUrl(url)
    }

    fun showLoading() {
        progressbar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressbar.visibility = View.GONE
    }

    fun showArticleView() {
        articleView.visibility = View.VISIBLE
    }

    fun hideArticleView() {
        articleView.visibility = View.GONE
    }

    fun showRetryButton() {
        retryButton.visibility = View.VISIBLE
    }

    fun hideRetryButton() {
        retryButton.visibility = View.GONE
    }

    companion object {
        const val URL_INPUT_KEY = "url"

        fun getCallingIntent(context: Context, url: String): Intent {
            val intent = Intent(context, ArticleActivity::class.java)
            return intent.putExtra(URL_INPUT_KEY, url)
        }
    }


}
