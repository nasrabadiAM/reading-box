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

package com.nasrabadiam.readingbox.data.db.article

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "article")
data class ArticleEntity(@PrimaryKey(autoGenerate = true)
                         @ColumnInfo(name = "_id")
                         var id: Int = 0,
                         @ColumnInfo(name = "title") var title: String = "",
                         @ColumnInfo(name = "link") var link: String,
                         @ColumnInfo(name = "desc") var description: String = "",
                         @ColumnInfo(name = "author") var author: String = "",
                         @ColumnInfo(name = "category") var category: String = "",
                         @ColumnInfo(name = "comments") var comments: String = "",
                         @Embedded var enclosure: EnclosureEntity = EnclosureEntity(),
                         @ColumnInfo(name = "guid") var guid: String = "",
                         @ColumnInfo(name = "pubDate") var pubDate: Date = Date(),
                         @ColumnInfo(name = "source") var source: String = "")
