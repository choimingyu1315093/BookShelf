package com.choisong.bookshelf.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.ItemWishHaveBookBinding
import com.choisong.bookshelf.model.TestBestsellerModel
import com.choisong.bookshelf.model.WishBookDataResult

class WishHaveBookAdapter(private val wishBookList: ArrayList<WishBookDataResult>, private val onClickListener: OnClickListener): RecyclerView.Adapter<WishHaveBookAdapter.ViewHolder>() {

    interface OnClickListener {
        fun wishItemClick(bookIsbn: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWishHaveBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(wishBookList[position])
        holder.itemView.setOnClickListener {
            onClickListener.wishItemClick(wishBookList[position].books.book_isbn)
        }
    }

    override fun getItemCount(): Int {
        return wishBookList.size
    }

    inner class ViewHolder(private val binding: ItemWishHaveBookBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(book: WishBookDataResult) = with(binding){
            Glide.with(ivBook).load(book.books.book_image).into(ivBook)
        }
    }
}