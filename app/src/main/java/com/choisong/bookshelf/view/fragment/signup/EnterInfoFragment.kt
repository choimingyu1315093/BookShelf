package com.choisong.bookshelf.view.fragment.signup

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentEnterInfoBinding
import com.choisong.bookshelf.model.EmailCheckModel
import com.choisong.bookshelf.model.SignUpModel
import com.choisong.bookshelf.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterInfoFragment : Fragment() {
    private var _binding: FragmentEnterInfoBinding? = null
    private val binding get() = _binding!!
    private val signUpViewModel: SignUpViewModel by viewModels()

    companion object {
        const val TAG = "EnterInfoFragment"
    }

    private var id = ""
    private var email = ""
    private var password = ""
    private var nickname = ""
    private var fcmToken = ""

    private var idCheck = false
    private var passwordCheck = false
    private var passwordMatchCheck = false
    private var nicknameCheck = false
    private var emailCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEnterInfoBinding.inflate(inflater, container, false)
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
        fcmToken = MyApplication.prefs.getFcmToken("fcmToken", "")

        etId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,15}$")
                if(regex.matches(s.toString())){
                    id = s.toString()
                    signUpViewModel.idCheck(id)
                    txtIdWarning.visibility = View.GONE
                }else {
                    id = ""
                    idCheck = false
                    txtIdWarning.visibility = View.VISIBLE
                    txtIdWarning.text = "❗아이디는 영문,숫자 포함하여 5~15자이어야 합니다."
                }
                signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
                if(emailRegex.matches(s.toString())){
                    email = s.toString()
                    val emailCheckModel = EmailCheckModel(email)
                    signUpViewModel.emailCheck(emailCheckModel)
                    txtEmailWarning.visibility = View.GONE
                }else {
                    email = ""
                    emailCheck = false
                    txtEmailWarning.visibility = View.VISIBLE
                    txtEmailWarning.text = "❗이메일 형식에 맞지 않습니다."
                }
                signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val passwordPattern = Regex("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,15}$")
                if(passwordPattern.matches(s.toString())){
                    passwordCheck = true
                    password = s.toString()
                    txtPasswordWarning.visibility = View.GONE

                    if(etPasswordCheck.text.toString() != "" && etPasswordCheck.text.toString() != s.toString()){
                        passwordCheck = false
                        txtPasswordCheckWarning.visibility = View.VISIBLE
                    }
                }else {
                    passwordCheck = false
                    password = ""
                    txtPasswordWarning.visibility = View.VISIBLE
                }
                signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etPasswordCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val currentPassword = etPassword.text.toString()
                if(currentPassword != s.toString()){
                    passwordMatchCheck = false
                    txtPasswordCheckWarning.visibility = View.VISIBLE
                }else {
                    passwordMatchCheck = true
                    txtPasswordCheckWarning.visibility = View.GONE
                }
                signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString() != ""){
                    nickname = s.toString()
                    signUpViewModel.nicknameCheck(nickname)
                }else {
                    nickname = ""
                    nicknameCheck = false
                }
                signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_enterInfoFragment_to_agreePageFragment)
        }

        btnSignUp.setOnClickListener {
            val signUpModel = SignUpModel(fcmToken, "general", email, id, nickname, password)
            signUpViewModel.signUp(signUpModel)
        }
    }

    private fun observeViewModel() = with(binding){
        signUpViewModel.idCheckResult.observe(viewLifecycleOwner){
            if(it){
                idCheck = true
                txtIdWarning.visibility = View.GONE
            }else {
                idCheck = false
                txtIdWarning.visibility = View.VISIBLE
                txtIdWarning.text = "❗이미 사용 중인 아이디 입니다."
            }
            signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
        }

        signUpViewModel.emailCheckResult.observe(viewLifecycleOwner){
            if(it){
                emailCheck = true
                txtEmailWarning.visibility = View.GONE
            }else {
                emailCheck = false
                txtEmailWarning.visibility = View.VISIBLE
                txtEmailWarning.text = "❗️이미 사용 중인 이메일 입니다."
            }
            signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
        }

        signUpViewModel.nicknameCheckResult.observe(viewLifecycleOwner){
            if(it){
                nicknameCheck = true
                txtNicknameWarning.visibility = View.GONE
            }else {
                nicknameCheck = false
                txtNicknameWarning.visibility = View.VISIBLE
            }
            signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
        }

        signUpViewModel.signUpResult.observe(viewLifecycleOwner){
            Navigation.findNavController(binding.root).navigate(R.id.action_enterInfoFragment_to_signUpSuccessFragment)
        }
    }

    private fun signUpBtnSetting(idCheck: Boolean, nicknameCheck: Boolean, emailCheck: Boolean, passwordCheck: Boolean, passwordMatchCheck: Boolean) = with(binding){
        if(idCheck && nicknameCheck && emailCheck && passwordCheck && passwordMatchCheck){
            btnSignUp.setBackgroundResource(R.drawable.bg_main_no_10)
            btnSignUp.isEnabled = true
        }else {
            btnSignUp.setBackgroundResource(R.drawable.bg_e9e9e9_e9e9e9_10)
            btnSignUp.isEnabled = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}