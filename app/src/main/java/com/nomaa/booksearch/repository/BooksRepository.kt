package com.nomaa.booksearch.repository

import com.nomaa.booksearch.model.BooksDataResponse
import com.nomaa.booksearch.rest.RestAPI
import com.nomaa.booksearch.rest.RestApiResponseListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository object which is in charge of downloading the data using the [RestAPI] and delivering
 * the response or the error via the [RestApiResponseListener] interface
 */
object BooksRepository {
    fun getVolumes(searchQuery: String, apiKey: String, responseListener: RestApiResponseListener) {
        RestAPI().getVolumes(searchQuery, apiKey).enqueue(object : Callback<BooksDataResponse> {
            override fun onFailure(call: Call<BooksDataResponse>, t: Throwable) {
                responseListener.onResponseFailed()
            }

            override fun onResponse(
                call: Call<BooksDataResponse>,
                response: Response<BooksDataResponse>
            ) {
                if (response.isSuccessful) {
                    responseListener.onResponseReceived(response.body())
                } else {
                    responseListener.onResponseFailed()
                }
            }
        })
    }
}