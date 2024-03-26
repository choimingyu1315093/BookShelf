package com.choisong.bookshelf.view.fragment.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentDetailBinding
import com.choisong.bookshelf.model.AddHaveBookModel
import com.choisong.bookshelf.model.TestReviewModel
import com.choisong.bookshelf.view.adapter.ReviewAdapter
import com.choisong.bookshelf.view.dialog.ReviewDialog
import com.choisong.bookshelf.viewmodel.DetailViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

@AndroidEntryPoint
class DetailFragment : Fragment(), ReviewDialog.OnDialogCloseListener, ReviewAdapter.OnClickListener {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels()

    companion object {
        const val TAG = "DetailFragment"
    }

    private lateinit var accessToken: String

    private val detailType: DetailFragmentArgs by navArgs()
    private val bestseller: DetailFragmentArgs by navArgs()
    private val popularBook: DetailFragmentArgs by navArgs()
    private val generalBook: DetailFragmentArgs by navArgs()
    private val bookDetailData: DetailFragmentArgs by navArgs()

    private lateinit var addHaveBookModel: AddHaveBookModel

    lateinit var reviewAdapter: ReviewAdapter

    private var haveBestseller = false
    private var wishBestseller = false
    private lateinit var haveBookIdx: String
    private lateinit var wishBookIdx: String
    private var bookIsbn = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
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

        detailViewModel.haveBook(accessToken)
        detailViewModel.wishBook(accessToken)

        if(detailType.detailType == "bestseller"){
            bookIsbn = bestseller.bestseller!!.isbn13!!
            bookSetting(bestseller.bestseller!!.cover!!, bestseller.bestseller!!.description!!)
            detailViewModel.getBookDetail(bestseller.bestseller!!.isbn13!!)
        }else if(detailType.detailType == "popular"){
            bookIsbn = popularBook.popularBook!!.book_isbn
            bookSetting(popularBook.popularBook!!.book_image, popularBook.popularBook!!.book_content)
            detailViewModel.getBookDetail(popularBook.popularBook!!.book_isbn)
        }else if(detailType.detailType == "general"){
            bookIsbn = generalBook.generalBook!!.book_isbn
            bookSetting(generalBook.generalBook!!.book_image, generalBook.generalBook!!.book_content)
            detailViewModel.getBookDetail(generalBook.generalBook!!.book_isbn)
        }else {
            bookIsbn = bookDetailData.bookDetailData!!.book_isbn
            bookSetting(bookDetailData.bookDetailData!!.book_image, bookDetailData.bookDetailData!!.book_content)
            detailViewModel.getBookDetail(bookDetailData.bookDetailData!!.book_isbn)
        }

        view?.isFocusableInTouchMode = true
        view?.requestFocus()
        view?.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                findNavController().popBackStack()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun bindViews() = with(binding){
        swHave.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                if(detailType.detailType == "bestseller"){
                    addHaveBookModel = AddHaveBookModel(bestseller.bestseller!!.isbn13!!)
                }else if(detailType.detailType == "popular"){
                    addHaveBookModel = AddHaveBookModel(popularBook.popularBook!!.book_isbn)
                }else if(detailType.detailType == "general"){
                    addHaveBookModel = AddHaveBookModel(generalBook.generalBook!!.book_isbn)
                }else {
                    addHaveBookModel = AddHaveBookModel(bookDetailData.bookDetailData!!.book_isbn)
                }
                detailViewModel.addHaveBook(accessToken, addHaveBookModel)
            }else {
                detailViewModel.deleteHaveBook(accessToken, haveBookIdx.toInt())
            }
        }

        swWish.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                if(detailType.detailType == "bestseller"){
                    addHaveBookModel = AddHaveBookModel(bestseller.bestseller!!.isbn13!!)
                }else if(detailType.detailType == "popular"){
                    addHaveBookModel = AddHaveBookModel(popularBook.popularBook!!.book_isbn)
                }else if(detailType.detailType == "general"){
                    addHaveBookModel = AddHaveBookModel(generalBook.generalBook!!.book_isbn)
                }else {
                    addHaveBookModel = AddHaveBookModel(bookDetailData.bookDetailData!!.book_isbn)
                }
                detailViewModel.addWishBook(accessToken, addHaveBookModel)
            }else {
                detailViewModel.deleteWishBook(accessToken, wishBookIdx.toInt())
            }
        }

        btnReview.setOnClickListener {
            val dialog = ReviewDialog(bookIsbn, "리뷰 등록", "등록", 0, this@DetailFragment)
            dialog.show(requireActivity().supportFragmentManager, "ReviewDialog")
        }

        ivBack.setOnClickListener {
//            if(detailType.detailType == "bookFragment"){
//                Navigation.findNavController(binding.root).navigate(R.id.action_detailFragment_to_bookFragment)
//            }else {
//                Navigation.findNavController(binding.root).navigate(R.id.action_detailFragment_to_homeFragment)
//            }
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() = with(binding){
        detailViewModel.bookDetailResult.observe(viewLifecycleOwner){
            rb.rating = it.book_average_rate.toFloat()
            if(it.book_comments != null){
                reviewAdapter = ReviewAdapter(it.book_comments, this@DetailFragment)
                rvReview.apply {
                    layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    adapter = reviewAdapter
                }
            }
        }

        detailViewModel.haveBookResult.observe(viewLifecycleOwner){
            Log.d(TAG, "observeViewModel: have $it")
            if(it != null){
                if(detailType.detailType == "bestseller"){
                    for(i in 0 until it.size){
                        if(it[i].books.book_publisher == bestseller.bestseller!!.publisher && bestseller.bestseller!!.title!!.contains(it[i].books.book_name)){
                            haveBestseller = true
                            haveBookIdx = it[i].have_book_idx.toString()
                        }
                    }
                    swHave.isChecked = haveBestseller
                }else if(detailType.detailType == "popular"){
                    for(i in 0 until it.size){
                        if(it[i].books.book_publisher == popularBook.popularBook!!.book_publisher && it[i].books.book_name == popularBook.popularBook!!.book_name){
                            haveBestseller = true
                            haveBookIdx = it[i].have_book_idx.toString()
                        }
                    }
                    swHave.isChecked = haveBestseller
                }else if(detailType.detailType == "general"){
                    for(i in 0 until it.size){
                        if(it[i].books.book_publisher == generalBook.generalBook!!.book_publisher && it[i].books.book_name == generalBook.generalBook!!.book_name){
                            haveBestseller = true
                            haveBookIdx = it[i].have_book_idx.toString()
                        }
                    }
                    swHave.isChecked = haveBestseller
                }else {
                    for(i in 0 until it.size){
                        if(it[i].books.book_publisher == bookDetailData.bookDetailData!!.book_publisher && bookDetailData.bookDetailData!!.book_name.contains(it[i].books.book_name)){
                            haveBestseller = true
                            haveBookIdx = it[i].have_book_idx.toString()
                        }
                    }
                    swHave.isChecked = haveBestseller
                }
            }
        }

        detailViewModel.addHaveBookResult.observe(viewLifecycleOwner){
            haveBookIdx = it
            if(swWish.isChecked){
                swWish.isChecked = false
            }
        }

        detailViewModel.wishBookResult.observe(viewLifecycleOwner){
            Log.d(TAG, "observeViewModel: wish $it")
            if(it != null){
                if(detailType.detailType == "bestseller"){
                    for(i in 0 until it.size){
                        if(it[i].books.book_publisher == bestseller.bestseller!!.publisher && bestseller.bestseller!!.title!!.contains(it[i].books.book_name)){
                            wishBestseller = true
                            wishBookIdx = it[i].wish_book_idx.toString()
                        }
                    }
                    swWish.isChecked = wishBestseller
                }else if(detailType.detailType == "popular"){
                    for(i in 0 until it.size){
                        if(it[i].books.book_publisher == popularBook.popularBook!!.book_publisher && it[i].books.book_name == popularBook.popularBook!!.book_name){
                            wishBestseller = true
                            wishBookIdx = it[i].wish_book_idx.toString()
                        }
                    }
                    swWish.isChecked = wishBestseller
                }else if(detailType.detailType == "general"){
                    for(i in 0 until it.size){
                        if(it[i].books.book_publisher == generalBook.generalBook!!.book_publisher && it[i].books.book_name == generalBook.generalBook!!.book_name){
                            wishBestseller = true
                            wishBookIdx = it[i].wish_book_idx.toString()
                        }
                    }
                    swWish.isChecked = wishBestseller
                }else {
                    for(i in 0 until it.size){
                        Log.d(TAG, "observeViewModel: askdlasdkl ${bookDetailData.bookDetailData!!.book_name}, ${it[i].books.book_name}")
                        if(it[i].books.book_publisher == bookDetailData.bookDetailData!!.book_publisher && bookDetailData.bookDetailData!!.book_name.contains(it[i].books.book_name)){
                            wishBestseller = true
                            wishBookIdx = it[i].wish_book_idx.toString()
                        }
                    }
                    swWish.isChecked = wishBestseller
                }
            }
        }

        detailViewModel.addWishBookResult.observe(viewLifecycleOwner){
            wishBookIdx = it
            if(swHave.isChecked){
                swHave.isChecked = false
            }
        }

        detailViewModel.deleteCommentResult.observe(viewLifecycleOwner){
            if(it){
                detailViewModel.getBookDetail(bookIsbn)
            }
        }
    }

    private fun bookSetting(link: String, description: String) = with(binding){
        CoroutineScope(Dispatchers.Main).launch {
            if(link == ""){
                Glide.with(ivBook).load(R.drawable.no_image).into(ivBook)
            }else {
                Glide.with(ivBook).load(link).into(ivBook)
            }
        }

        tvDescription.text = description
        tvDescription.post{
            val lineCount = tvDescription.layout.lineCount
            if (lineCount > 0) {
                if (tvDescription.layout.getEllipsisCount(lineCount - 1) > 0) {
                    // 더보기 표시
                    txtMore.visibility = View.VISIBLE

                    // 더보기 클릭 이벤트
                    txtMore.setOnClickListener {
                        tvDescription.maxLines = Int.MAX_VALUE
                        txtMore.visibility = View.GONE
                    }
                }
            }
        }
    }

    //리뷰 등록 이후 호출
    override fun dialogClose(b: Boolean) {
        if(b){
            detailViewModel.getBookDetail(bookIsbn)
        }
    }

    //리뷰 업데이트 및 삭제
    override fun itemClick(type: String, idx: Int) {
        if(type == "update"){
            val dialog = ReviewDialog(bookIsbn, "리뷰 수정", "수정", idx, this@DetailFragment)
            dialog.show(requireActivity().supportFragmentManager, "ReviewDialog")
        }else {
            detailViewModel.deleteComment(accessToken, idx)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}