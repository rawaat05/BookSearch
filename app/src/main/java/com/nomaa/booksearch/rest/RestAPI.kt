package com.nomaa.booksearch.rest

import com.nomaa.booksearch.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RestAPI {
    private var booksAPI: BooksAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BOOKS_API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        booksAPI = retrofit.create(BooksAPI::class.java)
    }

    fun getVolumes(searchQuery: String, apiKey: String) = booksAPI.getVolumes(searchQuery, apiKey)
}