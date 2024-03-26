package com.choisong.bookshelf.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.ItemChargeLogBinding
import com.choisong.bookshelf.model.TestChargeLogModel

class ChargeLogAdapter(private val context: Context, private val chargeLogList: ArrayList<TestChargeLogModel>): RecyclerView.Adapter<ChargeLogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChargeLogBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chargeLogList[position])
    }

    override fun getItemCount(): Int {
        return chargeLogList.size
    }

    inner class ViewHolder(private val binding: ItemChargeLogBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(chargeLog: TestChargeLogModel) = with(binding){
            if(chargeLog.point.toString().startsWith("-")){
                tvPoint.text = chargeLog.point.toString()
            }else {
                tvPoint.setTextColor(context.resources.getColor(R.color.main))
                tvPoint.text = chargeLog.point.toString()
            }
        }
    }
}