package com.choisong.bookshelf.view.fragment.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.choisong.bookshelf.databinding.FragmentForgetBinding
import com.choisong.bookshelf.model.FindPasswordModel
import com.choisong.bookshelf.viewmodel.LoginViewModel
import com.choisong.bookshelf.viewmodel.LoginViewModel_Factory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class ForgetFragment : Fragment() {
    private var _binding: FragmentForgetBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    companion object {
        const val TAG = "ForgetFragment"
    }

    var isFind = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentForgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        cl.setOnClickListener {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(etId.windowToken, 0)
        }
    }

    private fun bindViews() = with(binding){
        btnFind.setOnClickListener {
            if(etId.text.toString().trim() != ""){
                if(etEmail.text.toString().trim() != ""){
                    val findPasswordModel = FindPasswordModel(etId.text.toString(), etEmail.text.toString())
                    loginViewModel.findPassword(findPasswordModel)
                    isFind = true
                }else {
                    Toast.makeText(requireContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(requireContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() = with(binding){
        loginViewModel.findPasswordResult.observe(viewLifecycleOwner){
            if(isFind){
                if(it){
                    Toast.makeText(requireContext(), "${etId.text.toString()} 님의 비밀번호를\n가입하신 이메일로 발송합니다.", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(requireContext(), "입력하신 정보와 일치하는 회원이 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}