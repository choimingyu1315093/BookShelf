package com.choisong.bookshelf.view.fragment.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentMemoBinding
import com.choisong.bookshelf.view.adapter.MemoAdapter
import com.choisong.bookshelf.view.adapter.ReviewAdapter
import com.choisong.bookshelf.view.dialog.MemoDialog
import com.choisong.bookshelf.view.dialog.ReviewDialog
import com.choisong.bookshelf.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoFragment(private val isbn: String) : Fragment(), MemoDialog.OnDialogCloseListener {
    private var _binding: FragmentMemoBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels()

    companion object {
        const val TAG = "MemoFragment"
    }

    lateinit var accessToken: String
    private var type = "all"
    lateinit var memoAdapter: MemoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getBookMemo(accessToken, isbn, type)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMemoBinding.inflate(inflater, container, false)
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
        btnWrite.setOnClickListener {
            val dialog = MemoDialog(isbn, this@MemoFragment)
            dialog.show(requireActivity().supportFragmentManager, "MemoDialog")
        }

        rg.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb1 -> {
                    type = "all"
                    detailViewModel.getBookMemo(accessToken, isbn, type)
                }
                R.id.rb2 -> {
                    type = "mine"
                    detailViewModel.getBookMemo(accessToken, isbn, type)
                }
            }
        }
    }

    private fun observeViewModel() = with(binding){
        detailViewModel.bookMemoResult.observe(viewLifecycleOwner){
            if(it.size != 0){
                rvMemo.visibility = View.VISIBLE
                tvEmpty.visibility = View.GONE

                memoAdapter = MemoAdapter(it)
                rvMemo.apply {
                    layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    adapter = memoAdapter
                }
            }else {
                rvMemo.visibility = View.GONE
                tvEmpty.visibility = View.VISIBLE
            }
        }
    }

    override fun memoReload(b: Boolean) {
        if(b){
            detailViewModel.getBookMemo(accessToken, isbn, type)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}