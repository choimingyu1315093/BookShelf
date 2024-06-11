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
import com.choisong.bookshelf.databinding.FragmentFindIdBinding
import com.choisong.bookshelf.model.FindIdModel
import com.choisong.bookshelf.view.fragment.home.BookFragmentDirections
import com.choisong.bookshelf.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindIdFragment : Fragment() {
    private var _binding: FragmentFindIdBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    companion object {
        const val TAG = "FindIdFragment"
    }

    lateinit var accessToken: String

    private var isEmail = false
    private var isNickname = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFindIdBinding.inflate(inflater, container, false)
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
            inputManager.hideSoftInputFromWindow(etNickname.windowToken, 0)
        }

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isEmail = s.toString() != ""
                checkNextButtonEnable()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isNickname = s.toString() != ""
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
            val findIdModel = FindIdModel(etNickname.text.toString().trim(), etEmail.text.toString().trim())
            loginViewModel.findId(findIdModel)
        }
    }

    private fun observeViewModel() = with(binding){
        loginViewModel.findIdResult.observe(viewLifecycleOwner){
            if(it){
                val action = FindIdFragmentDirections.actionFindIdFragmentToFindSuccessFragment("id")
                Navigation.findNavController(binding.root).navigate(action)
            }else {
                Toast.makeText(requireContext(), "입력하신 정보를 다시 한번 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkNextButtonEnable() = with(binding) {
        if(isEmail && isNickname){
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