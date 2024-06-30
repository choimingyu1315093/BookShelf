package com.choisong.bookshelf.view.fragment.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentProfileBinding
import com.choisong.bookshelf.model.UserSettingModel
import com.choisong.bookshelf.view.activity.HomeActivity
import com.choisong.bookshelf.view.adapter.VPAdapter3
import com.choisong.bookshelf.view.adapter.VPAdapter4
import com.choisong.bookshelf.view.dialog.UserDeleteDialog
import com.choisong.bookshelf.view.fragment.home.BookFragmentDirections
import com.choisong.bookshelf.view.fragment.home.HomeFragmentDirections
import com.choisong.bookshelf.viewmodel.NotificationViewModel
import com.choisong.bookshelf.viewmodel.ProfileViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(), ProfileMemoFragment.OnDetailListener {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()
    private val notificationViewModel: NotificationViewModel by viewModels()

    companion object {
        const val TAG = "ProfileFragment"
    }

    private lateinit var accessToken: String

    lateinit var vpAdapter: VPAdapter4

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

        profileViewModel.myProfile(accessToken)
        notificationViewModel.alarmCount(accessToken)

        vpAdapter = VPAdapter4(requireParentFragment(), this@ProfileFragment)
        vpType.adapter = vpAdapter
        TabLayoutMediator(tlType, vpType){tab, position ->
            when(position){
                0 -> {
                    tab.text = "활동"
                }
                1 -> {
                    tab.text = "메모"
                }
            }
        }.attach()
    }

    private fun bindViews() = with(binding){
        ivChat.setOnClickListener {
            (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
            Navigation.findNavController(binding.root).navigate(R.id.action_profileFragment_to_chatFragment)
        }

        ivBell.setOnClickListener {
            (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
            Navigation.findNavController(binding.root).navigate(R.id.action_profileFragment_to_notificationFragment)
        }

        btnProfile.setOnClickListener {
            (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
            Navigation.findNavController(binding.root).navigate(R.id.action_profileFragment_to_profileChangeFragment)
        }
    }
    
    private fun observeViewModel() = with(binding){
        profileViewModel.myProfile.observe(viewLifecycleOwner){
            tvName.text = it.user_name
            if(it.user_description == ""){
                tvOneLineReview.text = "한 줄 메시지를 입력해주세요."
            }else {
                tvOneLineReview.text = it.user_description
            }
        }

        notificationViewModel.alarmCountResult.observe(viewLifecycleOwner){
            if(it == 0){
                tvNotifyCount.visibility = View.GONE
            }else {
                tvNotifyCount.visibility = View.VISIBLE
                tvNotifyCount.text = it.toString()
            }
        }
    }

    override fun goDetail(bookIsbn: String) {
        val action = ProfileFragmentDirections.actionProfileFragmentToDetailFragment(null, null, null, bookIsbn, null)
        Navigation.findNavController(binding.root).navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}