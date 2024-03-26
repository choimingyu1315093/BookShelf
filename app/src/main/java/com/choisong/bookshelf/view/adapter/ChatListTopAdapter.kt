package com.choisong.bookshelf.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.choisong.bookshelf.databinding.ItemChatTopBinding
import com.choisong.bookshelf.model.ChatroomListDataResult
import com.choisong.bookshelf.model.RealChatroomListData
import com.choisong.bookshelf.model.RealChatroomListDataItem
import com.choisong.bookshelf.model.TestChatModel
import com.choisong.bookshelf.model.chat.ChatListDataResult

class ChatListTopAdapter(private val chatroomList: ArrayList<ChatListDataResult>): RecyclerView.Adapter<ChatListTopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChatTopBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chatroomList[position])
    }

    override fun getItemCount(): Int {
        return chatroomList.size
    }

    inner class ViewHolder(private val binding: ItemChatTopBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(chatroom: ChatListDataResult) = with(binding){
            Glide.with(ivBook).load(chatroom.books.book_image).into(ivBook)
            tvName.text = chatroom.opponent_user[0].users.user_name
        }
    }
}