package com.choisong.bookshelf.view.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.databinding.FragmentBookProcessBinding
import com.choisong.bookshelf.util.HorizontalSpaceItemDecoration
import com.choisong.bookshelf.view.adapter.HaveWishBookAdapter
import com.choisong.bookshelf.view.adapter.WishHaveBookAdapter
import com.choisong.bookshelf.viewmodel.BookProcessViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookProcessFragment(private val type: String) : Fragment(), WishHaveBookAdapter.OnClickListener, HaveWishBookAdapter.OnClickListener {
    private var _binding: FragmentBookProcessBinding? = null
    private val binding get() = _binding!!
    private val bookProcessViewModel: BookProcessViewModel by activityViewModels()
    
    companion object {
        const val TAG = "BookProcessFragment"
    }
    private lateinit var accessToken: String

    lateinit var wishHaveBookAdapter: WishHaveBookAdapter
    lateinit var haveWishBookAdapter: HaveWishBookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: 호출")
        accessToken = MyApplication.prefs.getAccessToken("accessToken", "")
        if(type == "읽고 싶은 책"){
            bookProcessViewModel.wishBook(accessToken, "wish")
        }else if(type == "읽고 있는 책"){
            bookProcessViewModel.wishBook(accessToken, "reading")
        }else {
            bookProcessViewModel.wishBook(accessToken, "read")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBookProcessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    private fun observeViewModel() = with(binding){
        bookProcessViewModel.wishBookResult.observe(viewLifecycleOwner){
            if(it!!.size != 0){
                rvBook.visibility = View.VISIBLE
                clNoLike.visibility = View.GONE
                wishHaveBookAdapter = WishHaveBookAdapter(it, this@BookProcessFragment)
                rvBook.apply {
                    layoutManager = GridLayoutManager(requireContext(), 3)
                    adapter = wishHaveBookAdapter
                    addItemDecoration(HorizontalSpaceItemDecoration(8))
                }
            }else {
                rvBook.visibility = View.GONE
                clNoLike.visibility = View.VISIBLE
                if(type == "읽고 싶은 책"){
                    tvEmpty.text = "읽고 싶은 책이 없어요."
                }else if(type == "읽고 있는 책"){
                    tvEmpty.text = "읽고 있는 책이 없어요."
                }else {
                    tvEmpty.text = "읽은책이 없어요."
                }
            }
        }
    }

    //읽고 싶은 책 상세보기
    override fun wishItemClick(bookIsbn: String) {
        MyApplication.prefs.setDetail("detail", true)
        bookProcessViewModel.getBookDetail(accessToken, bookIsbn)
    }

    //보유 중인 책 상세보기
    override fun haveItemClick(bookIsbn: String) {
        MyApplication.prefs.setDetail("detail", true)
        bookProcessViewModel.getBookDetail(accessToken, bookIsbn)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}