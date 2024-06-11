package com.choisong.bookshelf.view.fragment.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentPasswordChangeSuccessBinding

class PasswordChangeSuccessFragment : Fragment() {
    private var _binding: FragmentPasswordChangeSuccessBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "PasswordChangeSuccessFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPasswordChangeSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
    }

    private fun bindViews() = with(binding){
        btnGo.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_passwordChangeSuccessFragment_to_settingFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}