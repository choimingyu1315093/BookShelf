package com.choisong.bookshelf.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.ItemSearchBookBinding
import com.choisong.bookshelf.model.GeneralResult
import com.choisong.bookshelf.model.PopularResult

class SearchMoreBookAdapter(private val bookList: ArrayList<GeneralResult>, private val onClickListener: OnClickListener): RecyclerView.Adapter<SearchMoreBookAdapter.ViewHolder>() {

    interface OnClickListener {
        fun generalBookClick(book: GeneralResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSearchBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookList[position])
        holder.itemView.setOnClickListener {
            onClickListener.generalBookClick(bookList[position])
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    inner class ViewHolder(private val binding: ItemSearchBookBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(book: GeneralResult) = with(binding){
            if(book.book_image == ""){
                Glide.with(ivBook).load(R.drawable.no_image).into(ivBook)
            }else {
                Glide.with(ivBook).load(book.book_image).into(ivBook)
            }

            tvTitle.text = book.book_name
            tvWriter.text = "${book.book_author} ⎪ ${book.book_publisher}"
            rb.setIsIndicator(true)
            rb.visibility = View.GONE
            if(book.book_tag == null){
                btnHaveBook.visibility = View.GONE
                btnWishBook.visibility = View.GONE
            }else if(book.book_tag == "wish_book"){
                btnWishBook.visibility = View.VISIBLE
            }else if(book.book_tag == "have_book"){
                btnHaveBook.visibility = View.VISIBLE
            }
        }
    }
}