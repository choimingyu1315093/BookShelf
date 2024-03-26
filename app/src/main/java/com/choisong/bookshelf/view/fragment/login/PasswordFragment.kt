package com.choisong.bookshelf.view.fragment.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentPasswordBinding
import com.choisong.bookshelf.view.adapter.VPAdapter
import com.choisong.bookshelf.view.adapter.VPAdapter2
import com.choisong.bookshelf.viewmodel.LoginViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren


class PasswordFragment : Fragment() {
    private var _binding: FragmentPasswordBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "PasswordFragment"
    }

    lateinit var vpAdapter2: VPAdapter2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding){
        vpAdapter2 = VPAdapter2(requireParentFragment())
        vpPassword.adapter = vpAdapter2
        TabLayoutMediator(tlPassword, vpPassword){tab, position ->
            when(position){
                0 -> {
                    tab.text = "비밀번호 찾기"
                }
                1 -> {
                    tab.text = "비밀번호 변경"
                }
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}