package com.choisong.bookshelf.view.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.databinding.FragmentBookProcessBinding
import com.choisong.bookshelf.model.TestBestsellerModel
import com.choisong.bookshelf.model.WishBookDataResult
import com.choisong.bookshelf.util.HorizontalSpaceItemDecoration
import com.choisong.bookshelf.view.adapter.HaveWishBookAdapter
import com.choisong.bookshelf.view.adapter.WishHaveBookAdapter
import com.choisong.bookshelf.viewmodel.BookProcessViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookProcessFragment(private val isWish: Boolean) : Fragment(), WishHaveBookAdapter.OnClickListener, HaveWishBookAdapter.OnClickListener {
    private var _binding: FragmentBookProcessBinding? = null
    private val binding get() = _binding!!
    private val bookProcessViewModel: BookProcessViewModel by viewModels()

    companion object {
        const val TAG = "BookProcessFragment"
    }

    lateinit var wishHaveBookAdapter: WishHaveBookAdapter
    lateinit var haveWishBookAdapter: HaveWishBookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBookProcessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observeViewModel()
    }

    private fun init() = with(binding){

    }

    private fun observeViewModel() = with(binding){
        if(isWish){
            bookProcessViewModel.wishBookResult.observe(viewLifecycleOwner){
                if(it != null && it.size != 0){
                    wishHaveBookAdapter = WishHaveBookAdapter(it, this@BookProcessFragment)
                    rvBook.apply {
                        layoutManager = GridLayoutManager(requireContext(), 3)
                        adapter = wishHaveBookAdapter
                        addItemDecoration(HorizontalSpaceItemDecoration(8))
                    }
                    clNoLike.visibility = View.GONE
                    rvBook.visibility = View.VISIBLE
                }else {
                    clNoLike.visibility = View.VISIBLE
                    tvEmpty.text = "읽고 싶은 책이 없어요."
                    rvBook.visibility = View.GONE
                }
            }
        }else {
            bookProcessViewModel.haveBookResult.observe(viewLifecycleOwner){
                if(it != null && it.size != 0){
                    haveWishBookAdapter = HaveWishBookAdapter(it, this@BookProcessFragment)
                    rvBook.apply {
                        layoutManager = GridLayoutManager(requireContext(), 3)
                        adapter = haveWishBookAdapter
                        addItemDecoration(HorizontalSpaceItemDecoration(8))
                    }
                    clNoLike.visibility = View.GONE
                    rvBook.visibility = View.VISIBLE
                }else {
                    clNoLike.visibility = View.VISIBLE
                    tvEmpty.text = "보유 중인 책이 없어요."
                    rvBook.visibility = View.GONE
                }
            }
        }
    }

    //읽고 싶은 책 상세보기
    override fun wishItemClick(bookIsbn: String) {
        MyApplication.prefs.setDetail("detail", true)
        bookProcessViewModel.getBookDetail(bookIsbn)
    }

    //보유 중인 책 상세보기
    override fun haveItemClick(bookIsbn: String) {
        MyApplication.prefs.setDetail("detail", true)
        bookProcessViewModel.getBookDetail(bookIsbn)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}