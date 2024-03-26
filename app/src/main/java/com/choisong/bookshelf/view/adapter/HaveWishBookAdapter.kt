package com.choisong.bookshelf.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.ItemWishHaveBookBinding
import com.choisong.bookshelf.model.HaveBookDataResult
import com.choisong.bookshelf.model.TestBestsellerModel

class HaveWishBookAdapter(private val haveBookList: ArrayList<HaveBookDataResult>, private val onClickListener: OnClickListener): RecyclerView.Adapter<HaveWishBookAdapter.ViewHolder>() {

    interface OnClickListener {
        fun haveItemClick(bookIsbn: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWishHaveBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(haveBookList[position])
        holder.itemView.setOnClickListener {
            onClickListener.haveItemClick(haveBookList[position].books.book_isbn)
        }
    }

    override fun getItemCount(): Int {
        return haveBookList.size
    }

    inner class ViewHolder(private val binding: ItemWishHaveBookBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(book: HaveBookDataResult) = with(binding){
            Glide.with(ivBook).load(book.books.book_image).into(ivBook)
        }
    }
}