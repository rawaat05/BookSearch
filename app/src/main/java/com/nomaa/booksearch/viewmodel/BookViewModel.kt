package com.nomaa.booksearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nomaa.booksearch.BuildConfig
import com.nomaa.booksearch.model.Book
import com.nomaa.booksearch.model.BooksDataResponse
import com.nomaa.booksearch.repository.BooksRepository
import com.nomaa.booksearch.rest.RestApiResponseListener

/**
 * ViewModel class which contains the [mBooks] LiveData and a method to download the book volume
 * data via the [BooksRepository] object. Implements the [RestApiResponseListener] interface to
 * receive success/failure from the API call as well as the response
 */
class BookViewModel : ViewModel(), RestApiResponseListener {
    /*
        List of books wrapped in a MutableLiveData object. When new data is downloaded, the value
        of the live data is updated which then sends the new value to the registered observers
     */
    internal var mBooks: MutableLiveData<List<Book>> = MutableLiveData()
    val isProgressVisible = MutableLiveData<Boolean>().apply { value = false }

    /**
     * Calls [BooksRepository.getVolumes] with the passed [queryString] string
     */
    fun downloadVolume(queryString: String) {
        isProgressVisible.value = true

        BooksRepository.getVolumes(
            queryString,
            BuildConfig.Books_API_KEY, this
        )
    }

    override fun onResponseReceived(response: BooksDataResponse?) {
        if (response != null) {
            // From the list of ItemsDataResponse objects, create a list of Books objects
            val books = response.items.map {
                val item = it.volumeInfo
                Book(
                    item.imageLinks?.thumbnail,
                    item.title,
                    item.authors
                )
            }
            // Set the new data on the live data object
            mBooks.value = books
        }

        isProgressVisible.value = false
    }

    override fun onResponseFailed() {
        isProgressVisible.value = false
    }
}
