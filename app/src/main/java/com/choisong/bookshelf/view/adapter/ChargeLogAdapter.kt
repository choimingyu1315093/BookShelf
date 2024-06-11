package com.choisong.bookshelf.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.ItemChargeLogBinding
import com.choisong.bookshelf.model.TestChargeLogModel
import com.choisong.bookshelf.model.TicketLogDataResult

class ChargeLogAdapter(private val context: Context, private val chargeLogList: ArrayList<TicketLogDataResult>): RecyclerView.Adapter<ChargeLogAdapter.ViewHolder>() {

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

        fun bind(ticketLogo: TicketLogDataResult) = with(binding){
            if(ticketLogo.ticket_log_count.toString().startsWith("-")){
                tvPoint.text = "${ticketLogo.ticket_log_count.toString()} 권"
            }else {
                tvPoint.setTextColor(context.resources.getColor(R.color.main))
                tvPoint.text = "${ticketLogo.ticket_log_count.toString()} 권"
            }
            tvDescription.text = ticketLogo.ticket_log_description

            val dateTimeParts = ticketLogo.create_date.split("T")
            val datePart = dateTimeParts[0]
            tvDate.text = "$datePart"
        }
    }
}