package com.nomaa.booksearch.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.nomaa.booksearch.R
import com.nomaa.booksearch.databinding.ItemBookBinding
import com.nomaa.booksearch.model.Book


class BooksGridAdapter(private val context: Context) :
    RecyclerView.Adapter<BooksGridAdapter.BooksViewHolder>() {

    internal var books: List<Book>? = null
        set(books) {
            field = books
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        /*
            Bind the view to the view holder by inflating the view in the ItemBookBinding and
            passing a reference to the binding to the view holder
         */
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemBookBinding.inflate(layoutInflater, parent, false)
        return BooksViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = books?.size ?: 0

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        // Get the book at the current position and bind to the view
        val book = books?.get(position)
        holder.bind(book, context)
    }

    class BooksViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root), RequestListener<Drawable> {

        /**
         * This function binds an instance of Book to the current view in RecyclerView.
         * It also uses Glide to load the thumbnail image from image url
         */
        internal fun bind(book: Book?, context: Context) {
            binding.book = book
            binding.executePendingBindings()

            /*
                Registering a listener here so that we can load the image into the image view when
                its available for consumption
             */
            Glide.with(context).load(book?.thumbnailUrl)
                .placeholder(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_book_placeholder
                    )
                )
                .fitCenter()
                .listener(this)
                .into(binding.thumbnail)
        }

        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            binding.thumbnail.setImageDrawable(resource)
            return true
        }
    }
}