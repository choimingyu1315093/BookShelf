package com.choisong.bookshelf.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.databinding.ItemActiveBinding
import com.choisong.bookshelf.model.ProfileActiveDataResult

class ProfileActiveAdapter(private val activeList: ArrayList<ProfileActiveDataResult>): RecyclerView.Adapter<ProfileActiveAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemActiveBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(activeList[position])
    }

    override fun getItemCount(): Int {
        return activeList.size
    }

    inner class ViewHolder(private val binding: ItemActiveBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(active: ProfileActiveDataResult) = with(binding){
            tvTitle.text = active.activity_title

            val dateTimeParts = active.create_date.split("T")
            val datePart = dateTimeParts[0]
            tvDate.text = "$datePart"

            tvContent.text = active.activity_content
        }
    }
}