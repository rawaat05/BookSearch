package com.nomaa.booksearch.rest

import com.nomaa.booksearch.model.BooksDataResponse

/**
 * An interface for communicating the result of the REST API call to implementing classes
 */
interface RestApiResponseListener {
    fun onResponseReceived(response: BooksDataResponse?)
    fun onResponseFailed()
}