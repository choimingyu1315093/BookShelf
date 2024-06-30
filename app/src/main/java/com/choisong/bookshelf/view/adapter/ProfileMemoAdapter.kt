package com.choisong.bookshelf.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.databinding.ItemMemoBinding
import com.choisong.bookshelf.model.ProfileMemoDataResult

class ProfileMemoAdapter(private val memoList: ArrayList<ProfileMemoDataResult>, private val onClickListener: OnClickListener): RecyclerView.Adapter<ProfileMemoAdapter.ViewHolder>() {

    interface OnClickListener {
        fun goDetail(bookIsbn: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(memoList[position])
        holder.itemView.setOnClickListener {
            onClickListener.goDetail(memoList[position].books.book_isbn)
        }
    }

    override fun getItemCount(): Int {
        return memoList.size
    }

    inner class ViewHolder(private val binding: ItemMemoBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(memo: ProfileMemoDataResult) = with(binding){
            tvContent.text = memo.memo_content

            val dateTimeParts = memo.create_date.split("T")
            val datePart = dateTimeParts[0]
            tvDate.text = "${memo.books.book_name}    $datePart"
        }
    }
}