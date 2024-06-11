package com.choisong.bookshelf.view.fragment.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentSecessionSuccessBinding
import com.choisong.bookshelf.view.activity.SplashActivity

class SecessionSuccessFragment : Fragment() {
    private var _binding: FragmentSecessionSuccessBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "SecessionSuccessFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSecessionSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
    }

    private fun bindViews() = with(binding){
        btnGo.setOnClickListener {
            val intent = requireActivity().packageManager.getLaunchIntentForPackage("com.choisong.bookshelf")
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            Runtime.getRuntime().exit(0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}