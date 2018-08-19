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

package com.nasrabadiam.readingbox;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ToolsTest {

    private static final String validUrl = "http://google.com";
    private static final String inValidUrl = "bla bla";

    @Test
    public static void testCorrectUrlIsValid() {
        boolean result = ToolsKt.isUrlValid(validUrl);
        Assert.assertTrue(result);
    }

    @Test
    public static void testInCorrectUrlIsInValid() {
        boolean result = ToolsKt.isUrlValid(inValidUrl);
        Assert.assertFalse(result);
    }
}
