package com.choisong.bookshelf.view.fragment.detail

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
import com.choisong.bookshelf.databinding.FragmentCommentBinding
import com.choisong.bookshelf.view.adapter.ReviewAdapter
import com.choisong.bookshelf.view.dialog.DeleteChatroomDialog
import com.choisong.bookshelf.view.dialog.ReviewDialog
import com.choisong.bookshelf.viewmodel.DetailViewModel
import com.google.maps.model.PlaceDetails.Review
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlin.math.round

@AndroidEntryPoint
class CommentFragment(private val isbn: String) : Fragment(), ReviewDialog.OnDialogCloseListener {
    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels()

    companion object {
        const val TAG = "CommentFragment"
    }

    lateinit var accessToken: String
    lateinit var reviewAdapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getBookDetail(accessToken, isbn)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCommentBinding.inflate(inflater, container, false)
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
        rb.setIsIndicator(true)
    }

    private fun bindViews() = with(binding){
        btnWrite.setOnClickListener {
            val dialog = ReviewDialog(isbn, this@CommentFragment)
            dialog.show(requireActivity().supportFragmentManager, "ReviewDialog")
        }
    }

    private fun observeViewModel() = with(binding){
        detailViewModel.bookDetailResult.observe(viewLifecycleOwner){
            rb.rating = it.book_average_rate.toFloat()
            val percent = it.book_average_rate
            val roundedPercent = round(percent).toInt()
//            tvAverage.text = roundedPercent.toString()
            tvAverage.text = it.book_average_rate.toString()
            if(it.book_comments != null){
                if(it.book_comments.size != 0){
                    rvReview.visibility = View.VISIBLE
                    tvEmpty.visibility = View.GONE

                    reviewAdapter = ReviewAdapter(it.book_comments)
                    rvReview.apply {
                        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                        adapter = reviewAdapter
                    }
                }else {
                    rvReview.visibility = View.GONE
                    tvEmpty.visibility = View.VISIBLE
                }
            }else {
                rvReview.visibility = View.GONE
                tvEmpty.visibility = View.VISIBLE
            }
        }
    }

    override fun commentReload(b: Boolean) {
        if(b){
            detailViewModel.getBookDetail(accessToken, isbn)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}