package com.nomaa.booksearch.model

import com.nomaa.booksearch.utils.Constants

/**
 * This class represents a Book in the UI
 */
class Book(val thumbnailUrl: String?, val title: String, private val authors: List<String>?) {
    /**
     * Returns a string joined by combining the strings in the [authors] list using ", "
     */
    fun getAuthorsFormattedString() = authors?.joinToString(Constants.AUTHORS_STRING_SEPARATOR) ?: ""
}