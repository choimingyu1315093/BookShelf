package com.choisong.bookshelf.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.choisong.bookshelf.view.fragment.detail.CommentFragment
import com.choisong.bookshelf.view.fragment.detail.MemoFragment

class VPAdapter3(private val fragment: Fragment, private val isbn: String): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 0){
            return CommentFragment(isbn)
        }else {
            return MemoFragment(isbn)
        }
    }
}