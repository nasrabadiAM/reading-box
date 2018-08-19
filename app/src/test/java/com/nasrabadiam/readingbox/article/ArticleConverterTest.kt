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

import com.nasrabadiam.readingbox.article.domain.Article
import org.junit.Assert
import org.junit.Test

class ArticleConverterTest {

    private val id = 5
    private val title = "test title"
    private val link = "http://google.com/"
    private val description = "http://google.com/"
    private val guid = "http://google.com/-google"


    @Test
    fun testGetDomainVersion() {
        val domainArticle = Article(id, title, link, description, guid = guid)
        val actual = ArticleConverter.getViewVersion(domainArticle)
        Assert.assertEquals(actual.id, id)
        Assert.assertEquals(actual.title, title)
        Assert.assertEquals(actual.link, link)
        Assert.assertEquals(actual.description, description)
        Assert.assertEquals(actual.guid, guid)
    }

    @Test
    fun testGetViewVersion() {
        val viewArticle = ArticleViewModel(id, title, link, description,
                guid = guid)
        val actual = ArticleConverter.getDomainVersion(viewArticle)
        Assert.assertEquals(actual.id, id)
        Assert.assertEquals(actual.title, title)
        Assert.assertEquals(actual.link, link)
        Assert.assertEquals(actual.description, description)
        Assert.assertEquals(actual.guid, guid)
    }

}
