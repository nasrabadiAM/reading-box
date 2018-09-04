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
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.nasrabadiam.readingbox.R
import com.nasrabadiam.readingbox.article.ArticleViewModel
import com.nasrabadiam.readingbox.loadUrl
import java.util.*
import kotlin.collections.ArrayList


class ArticleAdapter : RecyclerView.Adapter<ArticleItemViewHolder>() {

    private var items: List<ArticleViewModel> = Collections.emptyList()

    lateinit var popupMenu: PopupMenu

    var clickListener: OnItemClickListener? = null
    var menuItemClickListener: OnItemMenuClicked? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleItemViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
        return ArticleItemViewHolder(view)
    }

    /*you can choose which item layout should be load based on position here*/
    override fun getItemViewType(position: Int): Int {
        return R.layout.article_list_item
    }

    override fun onBindViewHolder(holder: ArticleItemViewHolder, position: Int) {
        holder.title.text = items[position].title
        holder.summary.text = items[position].description
        holder.image.loadUrl(items[position].enclosure.url)
        holder.parent.setOnClickListener {
            clickListener?.onClick(it, items[position])
        }

        popupMenu = PopupMenu(holder.parent.context, holder.parent)
        popupMenu.inflate(R.menu.articles_option)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.archive -> {
                    menuItemClickListener?.onArchive(holder.parent, items[position], position)
                    true
                }
                R.id.delete -> {
                    menuItemClickListener?.onDelete(holder.parent, items[position], position)
                    true
                }
                else -> false
            }
        }

        holder.parent.setOnLongClickListener {
            popupMenu.show()
            true
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateList(newData: List<ArticleViewModel>) {
        val diffResult = DiffUtil.calculateDiff(ArticleDiffUtil(newData, this.items))
        diffResult.dispatchUpdatesTo(this)
        this.items = ArrayList()
        (this.items as ArrayList<ArticleViewModel>).addAll(newData)
    }

    fun getItems(): List<ArticleViewModel> {
        return items
    }

}

class ArticleItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.title)
    val summary: TextView = view.findViewById(R.id.summary)
    val image: ImageView = view.findViewById(R.id.image)
    val parent: ViewGroup = view.findViewById(R.id.parent)
}


interface OnItemClickListener {
    fun onClick(view: View, article: ArticleViewModel)
}

interface OnItemLongClickListener {
    fun onLongClick(view: View, article: ArticleViewModel)
}

interface OnItemMenuClicked {
    fun onDelete(view: View, article: ArticleViewModel, position: Int)
    fun onArchive(view: View, article: ArticleViewModel, position: Int)
}


class ArticleDiffUtil(private val newItems: List<ArticleViewModel>,
                      private val oldItems: List<ArticleViewModel>) : DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPostion: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPostion].id
    }

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPostion: Int): Boolean {
        return oldItems[oldItemPosition].compareTo(newItems[newItemPostion]) == 0
    }


    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {

        val newItem = newItems[newItemPosition]
        val oldItem = oldItems[oldItemPosition]

        val diff = Bundle()
        if (newItem.title != oldItem.title) {
            diff.putString("title", newItem.title)
        }
        if (newItem.description != oldItem.description) {
            diff.putString("description", newItem.description)
        }
        if (newItem.link != oldItem.link) {
            diff.putString("link", newItem.link)
        }
        if (newItem.guid != oldItem.guid) {
            diff.putString("guid", newItem.guid)
        }
        if (newItem.enclosure.url != oldItem.enclosure.url) {
            diff.putString("enclosure.url", newItem.enclosure.url)
        }
        if (newItem.id != oldItem.id) {
            diff.putInt("id", newItem.id)
        }
        return if (diff.size() == 0) {
            null
        } else diff
    }
}