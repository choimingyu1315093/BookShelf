package com.choisong.bookshelf.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.choisong.bookshelf.view.fragment.home.BookProcessFragment
import com.choisong.bookshelf.view.fragment.login.ChangeFragment
import com.choisong.bookshelf.view.fragment.login.ForgetFragment

class VPAdapter2(private val fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 0){
            return ForgetFragment()
        }else {
            return ChangeFragment()
        }
    }
}