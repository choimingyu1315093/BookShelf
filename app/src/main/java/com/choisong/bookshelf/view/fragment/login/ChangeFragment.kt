package com.choisong.bookshelf.view.fragment.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentChangeBinding
import com.choisong.bookshelf.model.ChangePasswordModel
import com.choisong.bookshelf.model.FindPasswordModel
import com.choisong.bookshelf.view.activity.LoginActivity
import com.choisong.bookshelf.view.adapter.VPAdapter
import com.choisong.bookshelf.view.adapter.VPAdapter2
import com.choisong.bookshelf.viewmodel.LoginViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeFragment : Fragment() {
    private var _binding: FragmentChangeBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    companion object {
        const val TAG = "ChangeFragment"
    }
    
    lateinit var accessToken: String
    private var firstNext = false
    private var secondNext = false

    private var isChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChangeBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        accessToken = MyApplication.prefs.getAccessToken("accessToken", "")
        Log.d(TAG, "init: accessToken $accessToken")
        
        cl.setOnClickListener {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(etPassword.windowToken, 0)
        }
    }

    private fun bindViews() = with(binding){
        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val passwordPattern = Regex("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,15}$")
                if(passwordPattern.matches(s.toString())){
                    txtPasswordWarning.visibility = View.GONE
                    firstNext = true
                }else {
                    txtPasswordWarning.visibility = View.VISIBLE
                    firstNext = false
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etPasswordCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(etPassword.text.toString() == s.toString()){
                    txtPasswordCheckWarning.visibility = View.GONE
                    secondNext = true
                }else {
                    txtPasswordCheckWarning.visibility = View.VISIBLE
                    secondNext = false
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        btnChange.setOnClickListener {
            if(etPassword.text.toString().trim() != ""){
                if(etPasswordCheck.text.toString().trim() != ""){
                    if(etPassword.text.toString() == etPasswordCheck.text.toString()){
                        if(firstNext && secondNext){
                            isChanged = true
                            val changePasswordModel = ChangePasswordModel(etPassword.text.toString())
                            loginViewModel.changePassword(accessToken, changePasswordModel)
                        }
                    }
                }else {
                    Toast.makeText(requireContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(requireContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() = with(binding){
        loginViewModel.changePasswordResult.observe(viewLifecycleOwner){
            if(isChanged){
                if(it){
                    Toast.makeText(requireContext(), "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                }
                isChanged = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}