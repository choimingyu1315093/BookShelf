package com.choisong.bookshelf.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.choisong.bookshelf.view.fragment.home.BookProcessFragment

class VPAdapter(private val fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 0){
            return BookProcessFragment("읽고 싶은 책")
        }else if(position == 1){
            return BookProcessFragment("읽고 있는 책")
        }else {
            return BookProcessFragment("읽은 책")
        }
    }
}