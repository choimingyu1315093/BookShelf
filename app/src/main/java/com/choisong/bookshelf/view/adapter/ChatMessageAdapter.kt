package com.choisong.bookshelf.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.model.chat.ChatMessageDataResult
import com.squareup.picasso.Picasso

class ChatMessageAdapter(
    private val chatMessageList: ArrayList<ChatMessageDataResult>,
    private val onClickChatListener:OnClickChatListener,
    private val image: String?) :
    RecyclerView.Adapter<ChatMessageAdapter.ViewHolder>() {

    var textTime = ""

    interface OnClickChatListener {
        fun onFreeInfoClicked(modelIdx : Int, isChat : Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.item_chat_msg, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var chatMessage = chatMessageList[position]
        //2023-12-08T07:57:11.948Z
        val parts = chatMessage.create_date.split("T", "Z")
        var date = parts[0]
        var time = parts[1].substringBefore(".")
        var preDate = ""
        var preTime = ""
        var timeWithoutSecond = time.split(":")[0] + time.split(":")[1]
        var preTimeWithoutSecond = ""
        var differentDay = false
        var isDifferentTime = false
        var isDifferentUser = false

        if (position != 0) {
            preDate = chatMessageList[position-1].create_date.split("T", "Z")[0]
            Log.d(TAG, "onBindViewHolder: preDate $preDate, ${date.split("-")[2]}")
            if (preDate.split("-")[2] != date.split("-")[2]) {
                Log.d(TAG, "onBindViewHolder: 호출")
                differentDay = true
            }
            preTime = chatMessageList[position].create_date.split("T", "Z")[1].substringBefore(".")
            preTimeWithoutSecond = preTime.split(":")[0] + preTime.split(":")[1]
            if (preTimeWithoutSecond != timeWithoutSecond) {
                isDifferentTime = true
            }
        }


        //첫대화이거나 날짜가 다르면
//        if (position == 0 || differentDay) {
//            holder.tvDate.visibility = View.VISIBLE
//            holder.tvDate.text =
//                date.split("-")[0] + "년 " + date.split("-")[1] + "월 " + date.split("-")[2] + "일"
//
//            var set = ConstraintSet()
//            set.clone(holder.cl)
//            set.connect(
//                holder.tvSender.id,
//                ConstraintSet.TOP,
//                holder.tvDate.id,
//                ConstraintSet.BOTTOM
//            )
//            set.connect(
//                holder.tvReceiver.id,
//                ConstraintSet.TOP,
//                holder.tvDate.id,
//                ConstraintSet.BOTTOM
//            )
//            set.applyTo(holder.cl)
//        } else {
//            holder.tvDate.visibility = View.GONE
//        }

        //메세지는 반드시 null이 아니다
        if (chatMessage.message_content != "") {
            var firstItemPosition = 0
            var lastItemPosition = 0

            //첫 대화시 position은 0으로 잡힌다
            if(position != 0){
                for (i in position downTo 0) {
                    if (chatMessageList[i].users.user_idx != chatMessageList[position].users.user_idx) {
                        firstItemPosition = i + 1
                        break
                    }
                }

                for (i in position downTo 0) {
                    var itemTime = chatMessageList[i].create_date.split("T", "Z")[1].substringBefore(".")
                    var itemTimeWOSecond = itemTime.split(":")[0] + itemTime.split(":")[1]
                    if (itemTimeWOSecond != timeWithoutSecond) {
                        lastItemPosition = i + 1
                        break
                    }
                }
            }

            var hour = time.split(":")[0]
            var min = time.split(":")[1]

            if(position == 0 || differentDay){
                val parts = chatMessage.create_date.split("T", "Z")
                var date = parts[0]
                textTime = "$date $hour:$min"
            }else {
                textTime = "$hour:$min"
            }

            var message = chatMessage.message_content.replace("\\", "")

            if (MyApplication.prefs.getChatUserIdx("chatUserIdx", 0) == chatMessage.users.user_idx) {
                holder.tvSender.visibility = View.VISIBLE
                holder.tvSender.text = message
                holder.tvSendTime.visibility = View.VISIBLE
                holder.tvSendTime.text = textTime
                holder.ivPhoto.visibility = View.GONE
            } else {
                holder.ivPhoto.clipToOutline = true
                holder.ivPhoto.setOnClickListener {
                    onClickChatListener.onFreeInfoClicked(chatMessage.users.user_idx, true)
                }

                if (firstItemPosition == position || differentDay) {
//                    Picasso.get().load(Constants.BASEIMAGEURL + chatMessage.repr_img).into(holder.ivPhoto)
                    holder.ivPhoto.visibility = View.VISIBLE
                    if(image != null){
                        Glide.with(holder.ivPhoto).load(image).into(holder.ivPhoto)
                    }else {
                        Glide.with(holder.ivPhoto).load(R.drawable.no_image).into(holder.ivPhoto)
                    }
                } else {
                    holder.ivPhoto.layoutParams.height = 1
                }

                holder.tvReceiver.visibility = View.VISIBLE
                holder.tvReceiver.text = message
                holder.tvReceiveTime.visibility = View.VISIBLE
                holder.tvReceiveTime.text = textTime
            }
        }
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return if (chatMessageList.size > 0) {
            chatMessageList.size
        } else {
            0
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cl: ConstraintLayout = itemView.findViewById(R.id.cl)
//        var tvDate: TextView = itemView.findViewById(R.id.tvDate)
        var tvSender: TextView = itemView.findViewById(R.id.tvSender)
        var tvReceiver: TextView = itemView.findViewById(R.id.tvReceiver)
        var tvSendTime: TextView = itemView.findViewById(R.id.tvSendTime)
        var tvReceiveTime: TextView = itemView.findViewById(R.id.tvReceiveTime)
        var ivPhoto: ImageView = itemView.findViewById(R.id.ivPhoto)
    }

    companion object {
        private const val TAG = "ChatAdapter"
    }
}