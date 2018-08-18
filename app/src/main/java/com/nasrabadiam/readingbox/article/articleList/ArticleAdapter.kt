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

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.nasrabadiam.readingbox.R
import com.nasrabadiam.readingbox.article.ArticleViewModel
import com.nasrabadiam.readingbox.loadUrl
import java.util.*

class ArticleAdapter : RecyclerView.Adapter<ArticleItemViewHolder>() {

    var items: List<ArticleViewModel> = Collections.emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var clickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleItemViewHolder {
        return ArticleItemViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.article_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ArticleItemViewHolder, position: Int) {
        holder.title.text = items[position].title
        holder.summary.text = items[position].description
        holder.image.loadUrl(items[position].enclosure.url)
        holder.parent.setOnClickListener {
            clickListener?.onClick(holder.parent, items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class ArticleItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.title)
    val summary: TextView = view.findViewById(R.id.summary)
    val image: ImageView = view.findViewById(R.id.image)
    val parent: ViewGroup = view.findViewById(R.id.parent)
}


public interface OnItemClickListener{
    public fun onClick(view: View, article: ArticleViewModel)
}