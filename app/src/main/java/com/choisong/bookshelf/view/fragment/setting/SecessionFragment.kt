package com.choisong.bookshelf.view.fragment.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentSecessionBinding
import com.choisong.bookshelf.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecessionFragment : Fragment() {
    private var _binding: FragmentSecessionBinding? = null
    private val binding get() = _binding!!
    private val settingViewModel: SettingViewModel by viewModels()

    companion object {
        const val TAG = "SecessionFragment"
    }

    lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSecessionBinding.inflate(inflater, container, false)
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
    }

    private fun bindViews() = with(binding){
        btnOk.setOnClickListener {
            settingViewModel.deleteUser(accessToken)
        }

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() = with(binding){
        settingViewModel.deleteResult.observe(viewLifecycleOwner){
            Navigation.findNavController(binding.root).navigate(R.id.action_secessionFragment_to_secessionSuccessFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}