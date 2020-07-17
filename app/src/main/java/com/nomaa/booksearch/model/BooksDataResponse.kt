package com.nomaa.booksearch.model

/**
 * Data class for holding data object in the books API JSON response which hols a list of "items"
 * data objects
 */
data class BooksDataResponse(val items: List<ItemsDataResponse>)