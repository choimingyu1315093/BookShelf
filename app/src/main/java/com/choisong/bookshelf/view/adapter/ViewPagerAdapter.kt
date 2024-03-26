package com.choisong.bookshelf.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.choisong.bookshelf.R
import com.choisong.bookshelf.model.TestBestsellerModel

class ViewPagerAdapter(private val bestsellerList: ArrayList<TestBestsellerModel>, private val onClickListener: ViewPagerAdapter.OnClickListener): PagerAdapter() {

    interface OnClickListener {
        fun onClickItem(bestseller: TestBestsellerModel)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_bestseller, container, false)
        val ivImage = view.findViewById<ImageView>(R.id.ivBook)
        Glide.with(ivImage).load(bestsellerList[position].img).into(ivImage)
        view.findViewById<CardView>(R.id.cd).setOnClickListener {
            onClickListener.onClickItem(bestsellerList[position])
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun getCount(): Int {
        return bestsellerList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (view == `object`)
    }
}