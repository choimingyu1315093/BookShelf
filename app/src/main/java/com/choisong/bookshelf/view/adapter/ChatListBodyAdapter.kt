package com.choisong.bookshelf.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.choisong.bookshelf.databinding.ItemChatBodyBinding
import com.choisong.bookshelf.model.ChatroomListDataResult
import com.choisong.bookshelf.model.RealChatroomListData
import com.choisong.bookshelf.model.RealChatroomListDataItem
import com.choisong.bookshelf.model.TestChatModel
import com.choisong.bookshelf.model.chat.ChatListDataResult
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatListBodyAdapter(private val chatroomList: ArrayList<ChatListDataResult>, private val onClickListener: OnClickListener): RecyclerView.Adapter<ChatListBodyAdapter.ViewHolder>() {

    interface OnClickListener {
        fun goChatroom(chatroomIdx: Int, image: String, name: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChatBodyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chatroomList[position])
        holder.itemView.setOnClickListener {
            onClickListener.goChatroom(chatroomList[position].chat_room_idx, chatroomList[position].books.book_image, chatroomList[position].opponent_user[0].users.user_name)
        }
    }

    override fun getItemCount(): Int {
        return chatroomList.size
    }

    inner class ViewHolder(private val binding: ItemChatBodyBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(chatroom: ChatListDataResult) = with(binding){
            Glide.with(ivBook).load(chatroom.books.book_image).into(ivBook)
            tvName.text = chatroom.opponent_user[0].users.user_name
            tvMessage.text = chatroom.recent_message
            if(chatroom.recent_message_date != null){
                val parts = chatroom.recent_message_date.split("T", "Z")
                val date2 = parts[0]
                val time2 = parts[1].substringBefore(".")
                val formattedTime = "$date2 $time2"

                var dt = Date()
                var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                var now = sdf.format(dt).toString()
                var date = now.split(" ")[0]
                var time = now.split(" ")[1]

                var year = date.split("-")[0].toInt()
                var month = date.split("-")[1].toInt()
                var day = date.split("-")[2].toInt()

                var hour = time.split(":")[0].toInt()
                var minute = time.split(":")[1].toInt()
                var second = time.split(":")[2].toInt()

                var lastTime = formattedTime
                var rcvdDate = lastTime.split(" ")[0]
                var rcvdTime = lastTime.split(" ")[1]

                var rcvdYear = rcvdDate.split("-")[0].toInt()
                var rcvdMonth = rcvdDate.split("-")[1].toInt()
                var rcvdDay = rcvdDate.split("-")[2].toInt()

                var rcvdHour = rcvdTime.split(":")[0].toInt()
                var rcvdMinute = rcvdTime.split(":")[1].toInt()
                var rcvdSecond = rcvdTime.split(":")[2].toInt()

                tvTime.text =
                    if (rcvdYear == year) {
                        if (rcvdMonth == month) {
                            if (day == rcvdDay) {
                                if (hour == rcvdHour) {
                                    if (minute == rcvdMinute) {
                                        if (second == rcvdSecond) {
                                            "방금"
                                        } else {
                                            "${second - rcvdSecond}초 전"
                                        }
                                    } else {
                                        "${minute - rcvdMinute}분 전"
                                    }
                                } else {
                                    "${hour - rcvdHour}시간 전"
                                }
                            } else if (day - rcvdDay == 1) {
                                "어제"
                            } else {
                                rcvdDate
                            }
                        } else {
                            rcvdDate
                        }
                    } else {
                        rcvdDate
                    }

            }

            if(chatroom.me_user.unread_count != 0){
                tvMsgCount.visibility = View.VISIBLE
                tvMsgCount.text = chatroom.me_user.unread_count.toString()
            }else {
                tvMsgCount.visibility = View.GONE
            }
        }
    }
}