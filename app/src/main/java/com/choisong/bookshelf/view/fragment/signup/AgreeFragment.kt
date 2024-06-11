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
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentAgreeBinding
import com.choisong.bookshelf.model.EmailCheckModel
import com.choisong.bookshelf.model.SignUpModel
import com.choisong.bookshelf.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AgreeFragment : Fragment() {
    private var _binding: FragmentAgreeBinding? = null
    private val binding get() = _binding!!
    private val signUpViewModel: SignUpViewModel by viewModels()

    companion object {
        const val TAG = "AgreeFragment"
    }

    private var id = ""
    private var email = ""
    private var password = ""
    private var nickname = ""

    private var idCheck = false
    private var passwordCheck = false
    private var passwordMatchCheck = false
    private var nicknameCheck = false
    private var emailCheck = false
    private var next= false

    private var isAllChecked: Boolean = false
    private var isOneChecked: Boolean = false
    private var isTwoChecked: Boolean = false
    private var isThreeChecked: Boolean = false
    private var isFourChecked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAgreeBinding.inflate(inflater, container, false)
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

        etId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,15}$")
                if(regex.matches(s.toString())){
                    id = s.toString()
                    signUpViewModel.idCheck(id)
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val passwordPattern = Regex("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,15}$")
                if(passwordPattern.matches(s.toString())){
                    passwordMatchCheck = true
                    password = s.toString()
                    txtPasswordWarning.visibility = View.GONE
                    signUpBtnSetting(idCheck, nicknameCheck, emailCheck, next, passwordCheck, passwordMatchCheck)

                    if(etPasswordCheck.text.toString() != "" && etPasswordCheck.text.toString() != s.toString()){
                        passwordCheck = false
                        txtPasswordCheckWarning.visibility = View.VISIBLE
                        signUpBtnSetting(idCheck, nicknameCheck, emailCheck, next, passwordCheck, passwordMatchCheck)
                    }
                }else {
                    passwordMatchCheck = false
                    txtPasswordWarning.visibility = View.VISIBLE
                    signUpBtnSetting(idCheck, nicknameCheck, emailCheck, next, passwordCheck, passwordMatchCheck)
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etPasswordCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val currentPassword = etPassword.text.toString()
                if(currentPassword != s.toString()){
                    passwordCheck = false
                    txtPasswordCheckWarning.visibility = View.VISIBLE
                    signUpBtnSetting(idCheck, nicknameCheck, emailCheck, next, passwordCheck, passwordMatchCheck)
                }else {
                    passwordCheck = true
                    txtPasswordCheckWarning.visibility = View.GONE
                    signUpBtnSetting(idCheck, nicknameCheck, emailCheck, next, passwordCheck, passwordMatchCheck)
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                nickname = s.toString()
                signUpViewModel.nicknameCheck(nickname)
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
                }else {
                    emailCheck = false
                    signUpBtnSetting(idCheck, nicknameCheck, emailCheck, next, passwordCheck, passwordMatchCheck)
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun bindViews() = with(binding){
        cbAll.setOnCheckedChangeListener { _, isChecked ->
            isAllChecked = isChecked
            if(isAllChecked){
                setAllBoxes(true)
                next = true
            }else {
                if(isOneChecked && isTwoChecked && isThreeChecked && isFourChecked){
                    setAllBoxes(false)
                }
            }
        }

        cbTOU.setOnCheckedChangeListener { _, isChecked ->
            isOneChecked = isChecked
            checkBoxes()
        }

        cbPIU.setOnCheckedChangeListener { _, isChecked ->
            isTwoChecked = isChecked
            checkBoxes()
        }

        cbSPU.setOnCheckedChangeListener { _, isChecked ->
            isThreeChecked = isChecked
            checkBoxes()
        }

        cbSPU2.setOnCheckedChangeListener { _, isChecked ->
            isFourChecked = isChecked
            checkBoxes()
        }

        btnSignUp.setOnClickListener {
            val signUpModel = SignUpModel(MyApplication.prefs.getFcmToken("fcmToken", ""), "general", email, id, nickname, password)
            Log.d(TAG, "bindViews: signUpModel $signUpModel")
            signUpViewModel.signUp(signUpModel)
        }
    }

    private fun observeViewModel() = with(binding){
        signUpViewModel.idCheckResult.observe(viewLifecycleOwner){
            if(it){
                txtIdWarning.visibility = View.GONE
            }else {
                txtIdWarning.visibility = View.VISIBLE
            }
            idCheck = it
            signUpBtnSetting(idCheck, nicknameCheck, emailCheck, next, passwordCheck, passwordMatchCheck)
        }

        signUpViewModel.nicknameCheckResult.observe(viewLifecycleOwner){
            if(it){
                txtNicknameWarning.visibility = View.GONE
            }else {
                txtNicknameWarning.visibility = View.VISIBLE
            }
            nicknameCheck = it
            signUpBtnSetting(idCheck, nicknameCheck, emailCheck, next, passwordCheck, passwordMatchCheck)
        }

        signUpViewModel.emailCheckResult.observe(viewLifecycleOwner){
            if(it){
                txtEmailWarning.visibility = View.GONE
            }else {
                txtEmailWarning.visibility = View.VISIBLE
            }
            emailCheck = it
            signUpBtnSetting(idCheck, nicknameCheck, emailCheck, next, passwordCheck, passwordMatchCheck)
        }

        signUpViewModel.signUpResult.observe(viewLifecycleOwner){
            if(it){
                val dialog = SignUpSuccessFragment()
                dialog.show(requireActivity().supportFragmentManager, "SignUpSuccessFragment")
            }else {
                Toast.makeText(requireContext(), "회원가입에 실패 하셨습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setAllBoxes(b: Boolean) = with(binding) {
        cbSPU2.isChecked = b
        cbTOU.isChecked = b
        cbPIU.isChecked = b
        cbSPU.isChecked = b
        cbAll.isChecked = b
        signUpBtnSetting(idCheck, nicknameCheck, emailCheck, next, passwordCheck, passwordMatchCheck)
    }

    private fun checkBoxes() = with(binding) {
        cbAll.isChecked = isOneChecked && isTwoChecked && isThreeChecked && isFourChecked
        next = isOneChecked && isTwoChecked && isThreeChecked && isFourChecked
        signUpBtnSetting(idCheck, nicknameCheck, emailCheck, next, passwordCheck, passwordMatchCheck)
    }

    private fun signUpBtnSetting(idCheck: Boolean, nicknameCheck: Boolean, emailCheck: Boolean, next: Boolean, passwordCheck: Boolean, passwordMatchCheck: Boolean) = with(binding){
        if(idCheck && nicknameCheck && emailCheck && next && passwordCheck && passwordMatchCheck){
            if(etPassword.text.toString() == etPasswordCheck.text.toString()){
                btnSignUp.setBackgroundResource(R.drawable.bg_green_8)
                btnSignUp.isEnabled = true
            }else {
                btnSignUp.setBackgroundResource(R.drawable.bg_no_white_8)
                btnSignUp.isEnabled = false
            }
        }else {
            btnSignUp.setBackgroundResource(R.drawable.bg_no_white_8)
            btnSignUp.isEnabled = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}