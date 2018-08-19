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
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.nasrabadiam.readingbox.R
import com.nasrabadiam.readingbox.isUrlValid


class ArticleListActivity : AppCompatActivity(), ArticleListContract.Activity {

    private lateinit var articleListFragment: ArticleListFragment

    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var linkEdt: EditText

    private lateinit var addArticleBtn: Button
    private lateinit var progressbar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        val addArticleFab = findViewById<FloatingActionButton>(R.id.fab_add_article)
        initializeBottomSheet()

        articleListFragment = ArticleListFragment.getForAll()
        val fragmentTransaction = this.supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frame_layout, articleListFragment)
        fragmentTransaction.commit()

        addArticleFab.setOnClickListener {
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onBackPressed() {
        if (mBottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        else
            super.onBackPressed()

    }

    private fun initializeBottomSheet() {
        val bottomSheet = findViewById<View>(R.id.bottom_sheet)
        addArticleBtn = findViewById<View>(R.id.add_article) as Button
        linkEdt = findViewById<View>(R.id.link_edt) as EditText
        progressbar = findViewById<View>(R.id.progressbar) as ProgressBar
        addArticleBtn.setOnClickListener {
            addArticle(linkEdt.text.toString())
        }

        linkEdt.setOnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE) {
                addArticle(linkEdt.text.toString())
                true
            } else {
                false
            }
        }

        mBottomSheetBehavior = BottomSheetBehavior.from<View>(bottomSheet)
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        mBottomSheetBehavior.peekHeight = 0
    }

    fun addArticle(link: String) {
        if (!link.isUrlValid()) {
            Toast.makeText(this.applicationContext, getString(R.string.url_is_not_valid), Toast.LENGTH_SHORT).show()
            return
        }
        showLoading()
        articleListFragment.addArticle(link)
    }

    override fun showLoading() {
        linkEdt.visibility = View.GONE
        addArticleBtn.visibility = View.GONE
        progressbar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        linkEdt.visibility = View.VISIBLE
        addArticleBtn.visibility = View.VISIBLE
        progressbar.visibility = View.GONE
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun clearUrlEditText() {
        linkEdt.setText("")
    }
}
