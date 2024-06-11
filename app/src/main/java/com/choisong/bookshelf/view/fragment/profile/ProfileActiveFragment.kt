package com.choisong.bookshelf.view.fragment.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentProfileActiveBinding
import com.choisong.bookshelf.view.adapter.ProfileActiveAdapter
import com.choisong.bookshelf.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActiveFragment : Fragment() {
    private var _binding: FragmentProfileActiveBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()

    companion object {
        const val TAG = "ProfileActiveFragment"
    }

    private lateinit var accessToken: String
    private var userIdx = 0

    private lateinit var profileActiveAdapter: ProfileActiveAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        accessToken = MyApplication.prefs.getAccessToken("accessToken", "")
        userIdx = MyApplication.prefs.getUserIdx("userIdx", 0)
        profileViewModel.activeList(accessToken, userIdx)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileActiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    private fun observeViewModel() = with(binding){
        profileViewModel.activeList.observe(viewLifecycleOwner){
            if(it.size != 0){
                profileActiveAdapter = ProfileActiveAdapter(it)
                rvActive.apply {
                    layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    adapter = profileActiveAdapter
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}