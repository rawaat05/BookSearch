package com.nomaa.booksearch.rest

import com.nomaa.booksearch.model.BooksDataResponse
import com.nomaa.booksearch.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksAPI {
    @GET(Constants.BOOKS_API_ENDPOINT_VOLUMES)
    fun getVolumes(
        @Query(Constants.BOOKS_API_QUERY_SEARCH) query: String,
        @Query(Constants.BOOKS_API_QUERY_API_KEY) apiKey: String
    ): Call<BooksDataResponse>
}