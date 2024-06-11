package com.choisong.bookshelf.view.fragment.setting

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentPasswordChangeBinding
import com.choisong.bookshelf.model.ChangePasswordModel
import com.choisong.bookshelf.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordChangeFragment : Fragment() {
    private var _binding: FragmentPasswordChangeBinding? = null
    private val binding get() = _binding!!
    private val settingViewModel: SettingViewModel by viewModels()

    companion object {
        const val TAg = "PasswordChangeFragment"
    }

    lateinit var accessToken: String

    private var isCurrent = false
    private var isNew = false
    private var isCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPasswordChangeBinding.inflate(inflater, container, false)
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
        cl.setOnClickListener {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(etCheckPw.windowToken, 0)
        }

        etCurrentPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isCurrent = s.toString() != ""
                checkNextButtonEnable()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etNewPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val passwordPattern = Regex("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,15}$")
                if(passwordPattern.matches(s.toString())){
                    isNew = true
                    txtNewWarning.visibility = View.GONE
                }else {
                    isNew = false
                    txtNewWarning.visibility = View.VISIBLE
                }
                checkNextButtonEnable()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etCheckPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(etNewPw.text.toString() == s.toString()){
                    isCheck = true
                    txtCheckWarning.visibility = View.GONE
                }else {
                    isCheck = false
                    txtCheckWarning.visibility = View.VISIBLE
                }
                checkNextButtonEnable()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnChange.setOnClickListener {
            val changePasswordModel = ChangePasswordModel(etCurrentPw.text.toString().trim(), etCheckPw.text.toString().trim())
            settingViewModel.changePassword(accessToken, changePasswordModel)
        }
    }

    private fun observeViewModel() = with(binding){
        settingViewModel.changePasswordResult.observe(viewLifecycleOwner){
            if(it){
                Navigation.findNavController(binding.root).navigate(R.id.action_passwordChangeFragment_to_passwordChangeSuccessFragment)
            }else {
                Toast.makeText(requireContext(), "잘몬된 비밀번호입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkNextButtonEnable() = with(binding) {
        if(isCurrent && isNew && isCheck){
            btnChange.isEnabled = true
            btnChange.setBackgroundResource(R.drawable.bg_main_no_10)
        }else {
            btnChange.isEnabled = false
            btnChange.setBackgroundResource(R.drawable.bg_e9e9e9_e9e9e9_10)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}