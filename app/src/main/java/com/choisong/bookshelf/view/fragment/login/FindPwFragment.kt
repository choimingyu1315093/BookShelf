package com.choisong.bookshelf.view.fragment.login

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
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentFindPwBinding
import com.choisong.bookshelf.model.FindIdModel
import com.choisong.bookshelf.model.FindPasswordModel
import com.choisong.bookshelf.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindPwFragment : Fragment() {
    private var _binding: FragmentFindPwBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    companion object {
        const val TAG = "FindPwFragment"
    }

    lateinit var accessToken: String

    private var isId = false
    private var isEmail = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFindPwBinding.inflate(inflater, container, false)
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
            inputManager.hideSoftInputFromWindow(etId.windowToken, 0)
        }

        etId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isId = s.toString() != ""
                checkNextButtonEnable()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isEmail = s.toString() != ""
                checkNextButtonEnable()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnFind.setOnClickListener {
            val findPasswordModel = FindPasswordModel(etId.text.toString().trim(), etEmail.text.toString().trim())
            loginViewModel.findPassword(findPasswordModel)
        }
    }

    private fun observeViewModel() = with(binding){
        loginViewModel.findPasswordResult.observe(viewLifecycleOwner){
            if(it){
                val action = FindPwFragmentDirections.actionFindPwFragmentToFindSuccessFragment("password")
                Navigation.findNavController(binding.root).navigate(action)
            }else {
                Toast.makeText(requireContext(), "입력하신 정보를 다시 한번 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkNextButtonEnable() = with(binding) {
        if(isEmail && isId){
            btnFind.isEnabled = true
            btnFind.setBackgroundResource(R.drawable.bg_main_no_10)
        }else {
            btnFind.isEnabled = false
            btnFind.setBackgroundResource(R.drawable.bg_e9e9e9_e9e9e9_10)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}