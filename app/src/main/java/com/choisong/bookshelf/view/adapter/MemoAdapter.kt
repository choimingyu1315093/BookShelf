package com.choisong.bookshelf.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.databinding.ItemMemoBinding
import com.choisong.bookshelf.model.BookMemoDataResult

class MemoAdapter(private val memoList: ArrayList<BookMemoDataResult>): RecyclerView.Adapter<MemoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(memoList[position])
    }

    override fun getItemCount(): Int {
        return memoList.size
    }

    inner class ViewHolder(private val binding: ItemMemoBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(memo: BookMemoDataResult) = with(binding){
            tvContent.text = memo.memo_content

            val dateTimeParts = memo.create_date.split("T")
            val datePart = dateTimeParts[0]
            tvDate.text = "${memo.users.user_name}    $datePart"
        }
    }
}