package com.choisong.bookshelf.view.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.choisong.bookshelf.databinding.ItemBestsellerBinding
import com.choisong.bookshelf.model.BestsellerResultData
import com.choisong.bookshelf.model.TestBestsellerModel
import com.squareup.picasso.Picasso
import java.lang.Exception

class BestsellerAdapter(private val bestsellerList: List<BestsellerResultData>, private val onClickListener: OnClickListener): RecyclerView.Adapter<BestsellerAdapter.ViewHolder>() {

    interface OnClickListener {
        fun bestsellerClick(bestseller: BestsellerResultData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBestsellerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bestsellerList[position])
        holder.itemView.setOnClickListener {
            onClickListener.bestsellerClick(bestsellerList[position])
        }
    }

    override fun getItemCount(): Int {
        return bestsellerList.size
    }

    inner class ViewHolder(private val binding: ItemBestsellerBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(bestseller: BestsellerResultData) = with(binding){
            val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(ivBook).load(bestseller.cover).apply(options).into(ivBook)
        }
    }
}