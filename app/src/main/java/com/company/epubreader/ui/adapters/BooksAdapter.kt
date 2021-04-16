package com.company.epubreader.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.epubreader.R
import com.company.epubreader.ui.Constants
import com.company.epubreader.ui.models.Book
import com.squareup.picasso.Picasso


class BooksAdapter(val books: ArrayList<Book>) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    var onItemClick: ((Book) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val bookImage = itemView.findViewById<ImageView>(R.id.bookImg)
        val bookTitle = itemView.findViewById<TextView>(R.id.bookTitle)
        val bookAuthor = itemView.findViewById<TextView>(R.id.bookAuthor)

        init {


            itemView.setOnClickListener {
                onItemClick?.invoke(books[adapterPosition])
            }


        }

        fun bindData(book: Book) {
            Picasso.get().load(Constants.BASE_URL + book.image).into(bookImage)
            bookAuthor.text = book.author
            bookTitle.text = book.title

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(books[position])
    }

    override fun getItemCount() = books.size


}