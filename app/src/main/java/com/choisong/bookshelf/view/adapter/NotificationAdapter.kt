package com.choisong.bookshelf.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.databinding.ItemNotificationBinding
import com.choisong.bookshelf.model.AlarmListDataResult

class NotificationAdapter(private val alarmList: ArrayList<AlarmListDataResult>, private val onClickListener: OnClickListener): RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    interface OnClickListener {
        fun deleteItem(alarmIdx: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(alarmList[position])
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }

    inner class ViewHolder(private val binding: ItemNotificationBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(alarm: AlarmListDataResult) = with(binding){
            tvTitle.text = alarm.alarm_title
            tvContent.text = alarm.alarm_content
            ivThrash.setOnClickListener {
                onClickListener.deleteItem(alarm.alarm_idx)
            }
        }
    }
}