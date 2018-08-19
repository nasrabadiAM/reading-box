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

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nasrabadiam.readingbox.R
import com.nasrabadiam.readingbox.ReadingBoxApplication
import com.nasrabadiam.readingbox.article.ArticleDetailActivity
import com.nasrabadiam.readingbox.article.ArticleViewModel
import javax.inject.Inject

class ArticleListFragment : Fragment(), ArticleListContract.View {

    @Inject
    lateinit var presenter: ArticleListContract.Presenter

    private val adapter: ArticleAdapter = ArticleAdapter()
    private lateinit var activityContract: ArticleListContract.Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (activity is ArticleListActivity)
            activityContract = activity as ArticleListActivity

        (activity?.application as ReadingBoxApplication)
                .appComponent.inject(this)

        presenter.setView(this)
        presenter.getAllArticles()
        adapter.clickListener = object : OnItemClickListener {
            override fun onClick(view: View, article: ArticleViewModel) {
                val intent =
                        ArticleDetailActivity.getCallingIntent(activity!!, article.link)
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.recycler_layout, container)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun addArticle(link: String) {
        presenter.addArticle(link)
    }


    override fun showArticles(articles: List<ArticleViewModel>) {
        adapter.items = articles
    }

    override fun addNewArticleToItems(article: ArticleViewModel) {
        val list = adapter.items.toMutableList()
        list.add(0, article)
        adapter.items = list
        activityContract.hideLoading()
        activityContract.clearUrlEditText()
    }

    override fun articleAddedSuccessfully() {
        Toast.makeText(activity, getString(R.string.article_added_successfully), Toast.LENGTH_SHORT).show()
    }

    override fun articleAddFailed() {
        activityContract.hideLoading()
        Toast.makeText(activity, getString(R.string.article_add_failed), Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun getForAll(): ArticleListFragment {
            return ArticleListFragment()
        }
    }

}