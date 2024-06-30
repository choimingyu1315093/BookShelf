package com.choisong.bookshelf.view.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentProfileMemoBinding
import com.choisong.bookshelf.view.adapter.ProfileActiveAdapter
import com.choisong.bookshelf.view.adapter.ProfileMemoAdapter
import com.choisong.bookshelf.view.fragment.detail.DetailFragment
import com.choisong.bookshelf.view.fragment.home.HomeFragmentDirections
import com.choisong.bookshelf.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileMemoFragment(private val onDetailListener: OnDetailListener) : Fragment(), ProfileMemoAdapter.OnClickListener {
    private var _binding: FragmentProfileMemoBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()

    interface OnDetailListener {
        fun goDetail(bookIsbn: String)
    }

    companion object {
        const val TAG = "ProfileMemoFragment"
    }

    private lateinit var accessToken: String

    private lateinit var profileMemoAdapter: ProfileMemoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        accessToken = MyApplication.prefs.getAccessToken("accessToken", "")
        profileViewModel.memoList(accessToken)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileMemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    private fun observeViewModel() = with(binding){
        profileViewModel.memoList.observe(viewLifecycleOwner){
            if(it.size != 0){
                profileMemoAdapter = ProfileMemoAdapter(it, this@ProfileMemoFragment)
                rvMemo.apply {
                    layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    adapter = profileMemoAdapter
                }
            }
        }
    }

    override fun goDetail(bookIsbn: String) {
        onDetailListener.goDetail(bookIsbn)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}