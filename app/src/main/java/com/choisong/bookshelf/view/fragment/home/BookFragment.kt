package com.choisong.bookshelf.view.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.choisong.bookshelf.MyApplication
import com.google.android.material.tabs.TabLayoutMediator
import com.choisong.bookshelf.databinding.FragmentBookBinding
import com.choisong.bookshelf.view.adapter.VPAdapter
import com.choisong.bookshelf.viewmodel.BookProcessViewModel
import dagger.hilt.android.AndroidEntryPoint

class BookFragment : Fragment() {
    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!
    private val bookProcessViewModel: BookProcessViewModel by activityViewModels()

    companion object {
        const val TAG = "BookFragment"
    }

    lateinit var vpAdapter: VPAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observeViewModel()
    }

    private fun init() = with(binding){
        vpAdapter = VPAdapter(requireParentFragment())
        vpLike.adapter = vpAdapter
        TabLayoutMediator(tlLike, vpLike){tab, position ->
            when(position){
                0 -> {
                    tab.text = "읽고 싶은 책"
                }
                1 -> {
                    tab.text = "보유 중인 책"
                }
            }
        }.attach()
    }
    
    private fun observeViewModel() = with(binding){
        bookProcessViewModel.bookDetailResult.observe(viewLifecycleOwner){
            if(MyApplication.prefs.getDetail("detail", false)){
                val action = BookFragmentDirections.actionBookFragmentToDetailFragment(null, null, null, "bookFragment", it)
                Navigation.findNavController(binding.root).navigate(action)
                MyApplication.prefs.setDetail("detail", false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}