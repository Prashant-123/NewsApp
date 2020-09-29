package com.newsapp.utils

import org.junit.Assert.assertEquals
import org.junit.Test


class ConvertersTest {

    @Test
    fun parseTimestamp_Test() {
        val expected = "2020-09-21"

        val timestamp = "2020-09-21T21:00:12Z"
        val actual = Converters.parseTimestamp(timestamp)

        assertEquals(expected, actual)
    }
}