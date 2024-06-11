package com.choisong.bookshelf.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.ItemSearchBookBinding
import com.choisong.bookshelf.model.PopularResult
import com.choisong.bookshelf.model.TestBestsellerModel

class SearchBookAdapter(private val bookList: ArrayList<PopularResult>, private val onClickListener: OnClickListener): RecyclerView.Adapter<SearchBookAdapter.ViewHolder>() {

    interface OnClickListener {
        fun popularBookClick(book: PopularResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSearchBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookList[position])
        holder.itemView.setOnClickListener {
            onClickListener.popularBookClick(bookList[position])
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    inner class ViewHolder(private val binding: ItemSearchBookBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(book: PopularResult) = with(binding){
            if(book.book_image == ""){
                Glide.with(ivBook).load(R.drawable.no_image).into(ivBook)
            }else {
                Glide.with(ivBook).load(book.book_image).into(ivBook)
            }

            tvTitle.text = book.book_name
            tvWriter.text = "${book.book_author} ⎪ ${book.book_publisher}"
            rb.setIsIndicator(true)
            if(book.book_average_rate != null){
                rb.rating = book.book_average_rate.toFloat()
            }

            if(book.is_have_book != null && book.is_have_book){
                btnHaveBook.visibility = View.VISIBLE
            }else {
                btnHaveBook.visibility = View.GONE
            }

            if(book.read_type != null){
                when(book.read_type){
                    "wish" -> {
                        btnWishBook.visibility = View.VISIBLE
                    }
                    "reading" -> {
                        btnReadingBook.visibility = View.VISIBLE
                    }
                    "read" -> {
                        btnReadBook.visibility = View.VISIBLE
                    }
                }
            }else {
                btnWishBook.visibility = View.GONE
                btnReadingBook.visibility = View.GONE
                btnReadBook.visibility = View.GONE
            }
        }
    }
}