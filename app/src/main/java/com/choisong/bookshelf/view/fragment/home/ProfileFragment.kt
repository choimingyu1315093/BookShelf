package com.choisong.bookshelf.view.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentProfileBinding
import com.choisong.bookshelf.model.UserSettingModel
import com.choisong.bookshelf.view.activity.HomeActivity
import com.choisong.bookshelf.view.dialog.UserDeleteDialog
import com.choisong.bookshelf.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()

    companion object {
        const val TAG = "ProfileFragment"
    }

    private lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.VISIBLE
        accessToken = MyApplication.prefs.getAccessToken("accessToken", "")
        swChat.isChecked = MyApplication.prefs.getChatAlarm("chatAlarm", true)
        swMarketing.isChecked = MyApplication.prefs.getMarketingAlarm("marketingAlarm", true)
        swNear.isChecked = MyApplication.prefs.getNearAlarm("nearAlarm", true)
        swChatRequest.isChecked = MyApplication.prefs.getChatRequestAlarm("chatRequestAlarm", true)
    }

    private fun bindViews() = with(binding){
        cl3.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_profileFragment_to_chargeFragment)
            (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
        }

        cl4.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_profileFragment_to_chargeLogFragment)
            (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
        }

        cl5.setOnClickListener {

        }

        cl6.setOnClickListener {
            val dialog = UserDeleteDialog()
            dialog.show(requireActivity().supportFragmentManager, "UserDeleteDialog")
        }

        swChat.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                MyApplication.prefs.setChatAlarm("chatAlarm", true)
                val userSettingModel = UserSettingModel("setting_chat_alarm", true)
                profileViewModel.userSetting(accessToken, userSettingModel)
                Toast.makeText(requireContext(), "채팅 알람을 켜셨습니다.", Toast.LENGTH_SHORT).show()
            }else {
                MyApplication.prefs.setChatAlarm("chatAlarm", false)
                val userSettingModel = UserSettingModel("setting_chat_alarm", false)
                profileViewModel.userSetting(accessToken, userSettingModel)
                Toast.makeText(requireContext(), "채팅 알람을 끄셨습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        swMarketing.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                MyApplication.prefs.setMarketingAlarm("marketingAlarm", true)
                val userSettingModel = UserSettingModel("setting_marketing_alarm", true)
                profileViewModel.userSetting(accessToken, userSettingModel)
                Toast.makeText(requireContext(), "마케팅 알람을 켜셨습니다.", Toast.LENGTH_SHORT).show()
            }else {
                MyApplication.prefs.setMarketingAlarm("marketingAlarm", false)
                val userSettingModel = UserSettingModel("setting_marketing_alarm", false)
                profileViewModel.userSetting(accessToken, userSettingModel)
                Toast.makeText(requireContext(), "마케팅 알람을 끄셨습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        swNear.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                MyApplication.prefs.setNearAlarm("nearAlarm", true)
                val userSettingModel = UserSettingModel("setting_wish_book_alarm", true)
                profileViewModel.userSetting(accessToken, userSettingModel)
                Toast.makeText(requireContext(), "읽고 싶은 책 알람을 켜셨습니다.", Toast.LENGTH_SHORT).show()
            }else {
                MyApplication.prefs.setNearAlarm("nearAlarm", false)
                val userSettingModel = UserSettingModel("setting_wish_book_alarm", false)
                profileViewModel.userSetting(accessToken, userSettingModel)
                Toast.makeText(requireContext(), "읽고 싶은 책 알람을 끄셨습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        swChatRequest.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                MyApplication.prefs.setChatRequestAlarm("chatRequestAlarm", true)
                val userSettingModel = UserSettingModel("setting_chat_receive", true)
                profileViewModel.userSetting(accessToken, userSettingModel)
                Toast.makeText(requireContext(), "채팅 요청을 허용하셨습니다.", Toast.LENGTH_SHORT).show()
            }else {
                MyApplication.prefs.setChatRequestAlarm("chatRequestAlarm", false)
                val userSettingModel = UserSettingModel("setting_chat_receive", false)
                profileViewModel.userSetting(accessToken, userSettingModel)
                Toast.makeText(requireContext(), "채팅 요청을 차단하셨습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun observeViewModel() = with(binding){
        profileViewModel.userProfileResult.observe(viewLifecycleOwner){
            tvName.text = "${it.data.user_name}님, 환영합니다."
            if(it.data.ticket_count == 0){
                cl2.visibility = View.GONE
            }else {
                cl2.visibility = View.VISIBLE
                tvPoint.text = it.data.ticket_count.toString()
            }

//            swChat.isChecked = it.data.setting_chat_alarm == 1
//            swMarketing.isChecked = it.data.setting_marketing_alarm == 1
//            swNear.isChecked = it.data.setting_wish_book_alarm == 1
//            swChatRequest.isChecked = it.data.setting_chat_receive == 1
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}