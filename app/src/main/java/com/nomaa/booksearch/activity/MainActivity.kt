package com.nomaa.booksearch.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.nomaa.booksearch.R
import com.nomaa.booksearch.adapter.BooksGridAdapter
import com.nomaa.booksearch.model.Book
import com.nomaa.booksearch.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

/**
 * Includes a SearchView and a  RecyclerView as its main view components. When a search is performed
 * via the search view, book data is downloaded and populated in the recycler view in a grid layout.
 * This activity always launches in the "singleTop" mode so that search intents are always delivered
 * to it.
 */
class MainActivity : AppCompatActivity() {
    companion object Constants {
        private var KEY_SEARCH_QUERY = "search_query"
    }

    private var mAdapter: BooksGridAdapter? = null
    private lateinit var mBooksViewModel: BookViewModel

    private var mQueryString: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupRecyclerView()
        setupViewModel()
    }

    /**
     * Sets up the RecyclerView and the adapter
     */
    private fun setupRecyclerView() {
        // Set the layout for the RecyclerView to be a GridLayout
        // Number of columns is determined by the current screen width
        recyclerViewBooksGrid.layoutManager =
            GridLayoutManager(this, resources.getInteger(R.integer.numGridColumns))
        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = BooksGridAdapter(this)
        recyclerViewBooksGrid.adapter = mAdapter
    }

    /**
     * Sets up the ViewModel and begins to observe the data set
     */
    private fun setupViewModel() {
        mBooksViewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        mBooksViewModel.mBooks.observe(this,
            Observer<List<Book>> { books -> mAdapter?.books = books })
        mBooksViewModel.isProgressVisible.observe(this, Observer { visible ->
            progress.visibility = if (visible) VISIBLE else GONE
        })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (Intent.ACTION_SEARCH == intent?.action) {
            // If search was performed, get the query string from the intent and ask the view model
            // to download the books in the volumes matching the query
            mQueryString = intent.getStringExtra(SearchManager.QUERY)
            if (!mQueryString.isNullOrEmpty()) {
                mBooksViewModel.downloadVolume(mQueryString!!)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.item_search).actionView as SearchView
        searchView.apply {
            // Current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
            // If we have a query string from a previous entry, display it
            if (!mQueryString.isNullOrEmpty()) {
                setQuery(mQueryString, false)
            }
        }

        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save the query string in the out state
        outState.run {
            putString(KEY_SEARCH_QUERY, mQueryString)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Restore the query string from the saved instance state
        savedInstanceState?.run {
            mQueryString = getString(KEY_SEARCH_QUERY)
        }
    }
}
