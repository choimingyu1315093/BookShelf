package com.choisong.bookshelf.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.choisong.bookshelf.databinding.ItemNearBookBinding
import com.choisong.bookshelf.model.IWishBookHaveUserDataResult
import com.choisong.bookshelf.model.TestBestsellerModel

class NearBookAdapter(private val nearBookList: ArrayList<IWishBookHaveUserDataResult>, private val onClickListener: OnClickListener): RecyclerView.Adapter<NearBookAdapter.ViewHolder>() {

    interface OnClickListener{
        fun nearBookClick(nearBook: IWishBookHaveUserDataResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNearBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(nearBookList[position])
        holder.itemView.setOnClickListener {
            onClickListener.nearBookClick(nearBookList[position])
        }
    }

    override fun getItemCount(): Int {
        return nearBookList.size
    }

    inner class ViewHolder(private val binding: ItemNearBookBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(nearBook: IWishBookHaveUserDataResult) = with(binding){
            tvTitle.text = nearBook.book_name
            if(nearBook.book_author == ""){
                tvWriter.text = "${nearBook.book_publisher}"
            }else {
                tvWriter.text = "${nearBook.book_author} ⎪ ${nearBook.book_publisher}"
            }

            tvUser.text = "${nearBook.user_name}님이 보유 중인 책이에요."
            tvDistance.text = "${metersToKilometers(nearBook.distance!!.toDouble())}km"
            Glide.with(ivBook).load(nearBook.book_image).into(ivBook)
        }

        fun metersToKilometers(meters: Double): Double{
            return meters/1000
        }
    }
}