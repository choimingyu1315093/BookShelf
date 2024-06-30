package com.choisong.bookshelf.view.fragment.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentFindSuccessBinding

class FindSuccessFragment : Fragment() {
    private var _binding: FragmentFindSuccessBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "FindSuccessFragment"
    }

    private val type: FindSuccessFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFindSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        bindViews()
    }

    private fun init() = with(binding){
        Log.d(TAG, "asdsad ${MyApplication.prefs.getId("id", "")}")
        if(type.type == "id"){
            txtInfo.text = "회원님의 아이디는\n${MyApplication.prefs.getId("id", "")}입니다."
        }else {
            txtInfo.text = "${MyApplication.prefs.getEmail("email", "")}으로\n임시 비밀번호를 전송하였습니다."
            txtSubInfo.visibility = View.VISIBLE
            btnFindPw.visibility = View.GONE
        }
    }

    private fun bindViews() = with(binding){
        btnLogin.setOnClickListener {
            val intent = requireActivity().packageManager.getLaunchIntentForPackage("com.choisong.bookshelf")
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            Runtime.getRuntime().exit(0)
        }

        btnFindPw.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_findSuccessFragment_to_findPwFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}