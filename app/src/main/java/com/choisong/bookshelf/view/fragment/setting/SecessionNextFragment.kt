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
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentSecessionNextBinding
import com.choisong.bookshelf.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint

class SecessionNextFragment : Fragment() {
    private var _binding: FragmentSecessionNextBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "SecessionNextFragment"
    }

    lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSecessionNextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding){
        accessToken = MyApplication.prefs.getAccessToken("accessToken", "")
        cl.setOnClickListener {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(etPw.windowToken, 0)
        }

        etPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString() == ""){
                    btnOk.isEnabled = false
                    btnOk.setBackgroundResource(R.drawable.bg_e9e9e9_e9e9e9_10)
                }else {
                    btnOk.isEnabled = true
                    btnOk.setBackgroundResource(R.drawable.bg_red)
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}