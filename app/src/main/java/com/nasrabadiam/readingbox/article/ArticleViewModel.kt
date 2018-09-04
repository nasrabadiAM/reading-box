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

package com.nasrabadiam.readingbox.article


data class ArticleViewModel(val id: Int, val title: String, val link: String,
                            val description: String = "", val author: String = "",
                            val enclosure: EnclosureViewModel = EnclosureViewModel(),
                            val guid: String) : Comparable<ArticleViewModel> {
    override fun compareTo(other: ArticleViewModel): Int {
        return if (other.title == this.title &&
                other.description == this.description &&
                other.guid == this.guid &&
                other.link == this.link &&
                other.enclosure.url == this.enclosure.url) {
            0
        } else 1
    }
}
