package com.choisong.bookshelf.view.fragment.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentSettingBinding
import com.choisong.bookshelf.model.UserSettingModel
import com.choisong.bookshelf.view.activity.HomeActivity
import com.choisong.bookshelf.view.activity.LoginActivity
import com.choisong.bookshelf.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val settingViewModel: SettingViewModel by viewModels()

    companion object {
        const val TAG = "SettingFragment"
    }

    private lateinit var accessToken: String
    private var ticket = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
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
        (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.VISIBLE

        settingViewModel.getUserSetting(accessToken)
    }

    private fun bindViews() = with(binding){
        swChat.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                val userSettingModel = UserSettingModel("setting_chat_alarm", true)
                settingViewModel.patchUserSetting(accessToken, userSettingModel)
            }else {
                val userSettingModel = UserSettingModel("setting_chat_alarm", false)
                settingViewModel.patchUserSetting(accessToken, userSettingModel)
            }
        }

        swMarketing.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                val userSettingModel = UserSettingModel("setting_marketing_alarm", true)
                settingViewModel.patchUserSetting(accessToken, userSettingModel)
            }else {
                val userSettingModel = UserSettingModel("setting_marketing_alarm", false)
                settingViewModel.patchUserSetting(accessToken, userSettingModel)
            }
        }

        swNear.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                val userSettingModel = UserSettingModel("setting_wish_book_alarm", true)
                settingViewModel.patchUserSetting(accessToken, userSettingModel)
            }else {
                val userSettingModel = UserSettingModel("setting_wish_book_alarm", false)
                settingViewModel.patchUserSetting(accessToken, userSettingModel)
            }
        }

        swChatRequest.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                val userSettingModel = UserSettingModel("setting_chat_receive", true)
                settingViewModel.patchUserSetting(accessToken, userSettingModel)
            }else {
                val userSettingModel = UserSettingModel("setting_chat_receive", false)
                settingViewModel.patchUserSetting(accessToken, userSettingModel)
            }
        }

        cl3.setOnClickListener {
            val action = SettingFragmentDirections.actionSettingFragmentToChargeFragment(ticket)
            Navigation.findNavController(binding.root).navigate(action)
            (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
        }

        cl4.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_settingFragment_to_chargeLogFragment)
            (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
        }

        cl5.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_settingFragment_to_passwordChangeFragment)
            (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
        }

        cl6.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_settingFragment_to_secessionFragment)
            (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
        }

        cl7.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            MyApplication.prefs.setAutoLogin("autoLogin", false)
            MyApplication.prefs.setLoginId("loginId", "")
            MyApplication.prefs.setLoginPw("loginPw", "")
        }
    }

    private fun observeViewModel() = with(binding){
        settingViewModel.userSettingResult.observe(viewLifecycleOwner){
            ticket = it.ticket_count
            tvWelcome.text = "${it.user_name} 님, 환영합니다."
            btnMoney.text = "${it.ticket_count}장"
            swChat.isChecked = it.setting_chat_alarm == 1
            swMarketing.isChecked = it.setting_marketing_alarm == 1
            swNear.isChecked = it.setting_wish_book_alarm == 1
            swChatRequest.isChecked = it.setting_chat_receive == 1
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}