package com.choisong.bookshelf.view.fragment.detail

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentDetailBinding
import com.choisong.bookshelf.model.AddHaveBookModel
import com.choisong.bookshelf.model.AddMyBookModel
import com.choisong.bookshelf.view.activity.HomeActivity
import com.choisong.bookshelf.view.adapter.ReviewAdapter
import com.choisong.bookshelf.view.adapter.VPAdapter3
import com.choisong.bookshelf.view.dialog.ReviewDialog
import com.choisong.bookshelf.viewmodel.DetailViewModel
import com.choisong.bookshelf.viewmodel.ReviewViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.round

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels()
    private val reviewViewModel: ReviewViewModel by viewModels()
    
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
    private var myBookIdx = 0
    private var readType = "none"
    private var isHaveBook = "n"
    private var readingStartDate = ""
    private var readStartDate = ""
    private var readEndDate = ""

    private var readPage = 0
    private var totalPage = 0
    private var percent = 0

    lateinit var vpAdapter: VPAdapter3

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
        (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
        accessToken = MyApplication.prefs.getAccessToken("accessToken", "")

        if(detailType.detailType == "bestseller"){
            bookIsbn = bestseller.bestseller!!.isbn13!!
            detailViewModel.getBookDetail(accessToken, bookIsbn)
        }else if(detailType.detailType == "popular"){
            bookIsbn = popularBook.popularBook!!.book_isbn
            detailViewModel.getBookDetail(accessToken, bookIsbn)
        }else if(detailType.detailType == "general"){
            bookIsbn = generalBook.generalBook!!.book_isbn
            detailViewModel.getBookDetail(accessToken, bookIsbn)
        }else {
            bookIsbn = bookDetailData.bookDetailData!!.book_isbn
            detailViewModel.getBookDetail(accessToken, bookIsbn)
        }

        etStart.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                readingStartDate = s.toString()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etStartDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                readStartDate = s.toString()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etEndDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                readEndDate = s.toString()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        view?.isFocusableInTouchMode = true
        view?.requestFocus()
        view?.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                findNavController().popBackStack()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        vpAdapter = VPAdapter3(requireParentFragment(), bookIsbn)
        vpType.adapter = vpAdapter
        TabLayoutMediator(tlType, vpType){tab, position ->
            when(position){
                0 -> {
                    tab.text = "후기"
                }
                1 -> {
                    tab.text = "메모"
                }
            }
        }.attach()

        etPage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString() != ""){
                    readPage = s.toString().toInt()
                    tvPage.text = "$readPage/${totalPage}"
                    if(readPage == 0 || readPage.toString() == ""){
                        tvPercent.text = "0%"
                        progress.progress = 0
                    }else {
                        val percent = (readPage.toDouble()/totalPage.toDouble())*100
                        val roundedPercent = round(percent).toInt()
                        tvPercent.text = "${roundedPercent}%"
                        progress.progress = roundedPercent
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        clMain.setOnClickListener {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(etPage.windowToken, 0)
        }
    }

    private fun bindViews() = with(binding){
        swHave.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                isHaveBook = "y"
            }else {
                isHaveBook = "n"
                readType = "none"
            }
        }

        btnWishBook.setOnClickListener {
            btnWishBook.setBackgroundResource(R.drawable.bg_main_no_10)
            btnReadingBook.setBackgroundResource(R.drawable.bg_e9e9e9_e9e9e9_10)
            btnReadBook.setBackgroundResource(R.drawable.bg_e9e9e9_e9e9e9_10)
            clReading.visibility = View.GONE
            clRead.visibility = View.GONE
            readType = "wish"
            readingStartDate = ""
            readStartDate = ""
            readEndDate = ""
        }

        btnReadingBook.setOnClickListener {
            btnWishBook.setBackgroundResource(R.drawable.bg_e9e9e9_e9e9e9_10)
            btnReadingBook.setBackgroundResource(R.drawable.bg_main_no_10)
            btnReadBook.setBackgroundResource(R.drawable.bg_e9e9e9_e9e9e9_10)
            clReading.visibility = View.VISIBLE
            clRead.visibility = View.GONE
            readType = "reading"
            readStartDate = ""
            readEndDate = ""
        }

        btnReadBook.setOnClickListener {
            btnWishBook.setBackgroundResource(R.drawable.bg_e9e9e9_e9e9e9_10)
            btnReadingBook.setBackgroundResource(R.drawable.bg_e9e9e9_e9e9e9_10)
            btnReadBook.setBackgroundResource(R.drawable.bg_main_no_10)
            clReading.visibility = View.GONE
            clRead.visibility = View.VISIBLE
            readType = "read"
            readingStartDate = ""
        }

        etStart.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                etStart.setText("${year}-${month+1}-${dayOfMonth}")
                readingStartDate = etStart.text.toString()
            }
            DatePickerDialog(requireContext(), dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        etStartDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                etStartDate.setText("${year}-${month+1}-${dayOfMonth}")
                readStartDate = etStartDate.text.toString()
            }
            DatePickerDialog(requireContext(), dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        etEndDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                etEndDate.setText("${year}-${month+1}-${dayOfMonth}")
                readEndDate = etEndDate.text.toString()
            }
            DatePickerDialog(requireContext(), dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnSave.setOnClickListener {
            if(isHaveBook  == "n"){
                val addMyBookModel = AddMyBookModel(bookIsbn, isHaveBook, readType, totalPage, readPage, null, null)
                Log.d(TAG, "bindViews: addMyBookModel none $addMyBookModel")
                detailViewModel.addMyBook(accessToken, addMyBookModel)
            }else {
                when(readType){
                    "wish" -> {
                        val addMyBookModel = AddMyBookModel(bookIsbn, isHaveBook, readType, totalPage, readPage, null, null)
                        Log.d(TAG, "bindViews: addMyBookModel wish $addMyBookModel")
                        detailViewModel.addMyBook(accessToken, addMyBookModel)
                    }
                    "reading" -> {
                        if(readingStartDate == ""){
                            Toast.makeText(requireContext(), "시작일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                        }else {
                            val addMyBookModel = AddMyBookModel(bookIsbn, isHaveBook, readType, totalPage, readPage, readingStartDate, null)
                            Log.d(TAG, "bindViews: addMyBookModel reading $addMyBookModel")
                            detailViewModel.addMyBook(accessToken, addMyBookModel)
                        }
                    }
                    "read" -> {
                        if(readStartDate == "" || readEndDate == ""){
                            Toast.makeText(requireContext(), "시작일과 종료일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                        }else {
                            val addMyBookModel = AddMyBookModel(bookIsbn, isHaveBook, readType, totalPage, readPage, readStartDate, readEndDate)
                            Log.d(TAG, "bindViews: addMyBookModel read $addMyBookModel")
                            detailViewModel.addMyBook(accessToken, addMyBookModel)
                        }
                    }
                    else -> {
                        val addMyBookModel = AddMyBookModel(bookIsbn, isHaveBook, readType, totalPage, readPage, null, null)
                        Log.d(TAG, "bindViews: addMyBookModel none $addMyBookModel")
                        detailViewModel.addMyBook(accessToken, addMyBookModel)
                    }
                }
            }
        }

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() = with(binding){
        detailViewModel.addMyBookResult.observe(viewLifecycleOwner){
            if(it){
                Toast.makeText(requireContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        detailViewModel.bookDetailResult.observe(viewLifecycleOwner){
            CoroutineScope(Dispatchers.Main).launch {
                myBookIdx = it.my_book_idx
                readType = it.read_type
                if(it.book_image == ""){
                    Glide.with(ivBook).load(R.drawable.no_image).into(ivBook)
                }else {
                    Glide.with(ivBook).load(it.book_image).into(ivBook)
                }

                if(it.book_full_page == 0){
                    clPage.visibility = View.GONE
                }else {
                    clPage.visibility = View.VISIBLE
                    etPage.setText(it.read_page.toString())
                    totalPage = it.book_full_page
                    readPage = it.read_page
                    if(it.read_page == 0){
                        tvPage.text = "0/${totalPage}"
                        tvPercent.text = "0%"
                        progress.progress = 0
                    }else {
                        tvPage.text = "${readPage}/${totalPage}"
                        percent = readPage/totalPage
                        tvPercent.text = "${percent}%"
                        progress.progress = percent
                    }
                }

                tvTitle.text = it.book_name
                tvAuthor.text = "${it.book_author}"
                tvPublisher.text = "${it.book_publisher}"
                if(it.book_translator == ""){
                    txtTranslator.visibility = View.GONE
                    tvTranslator.visibility = View.GONE
                }else {
                    txtTranslator.visibility = View.VISIBLE
                    tvTranslator.visibility = View.VISIBLE
                    tvTranslator.text = it.book_translator
                }
                tvDescription.text = it.book_content
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

                swHave.isChecked = it.is_have_book == "y"
                when(it.read_type){
                    "wish" -> {
                        btnWishBook.setBackgroundResource(R.drawable.bg_main_no_10)
                        btnReadingBook.setBackgroundResource(R.drawable.bg_e9e9e9_e9e9e9_10)
                        btnReadBook.setBackgroundResource(R.drawable.bg_e9e9e9_e9e9e9_10)
                        clReading.visibility = View.GONE
                        clRead.visibility = View.GONE
                        readType = "wish"
                        readingStartDate = ""
                        readStartDate = ""
                        readEndDate = ""
                    }
                    "read" -> {
                        btnWishBook.setBackgroundResource(R.drawable.bg_e9e9e9_e9e9e9_10)
                        btnReadingBook.setBackgroundResource(R.drawable.bg_e9e9e9_e9e9e9_10)
                        btnReadBook.setBackgroundResource(R.drawable.bg_main_no_10)
                        clReading.visibility = View.GONE
                        clRead.visibility = View.VISIBLE
                        readType = "read"
                        readingStartDate = ""
                    }
                    "reading" -> {
                        btnWishBook.setBackgroundResource(R.drawable.bg_e9e9e9_e9e9e9_10)
                        btnReadingBook.setBackgroundResource(R.drawable.bg_main_no_10)
                        btnReadBook.setBackgroundResource(R.drawable.bg_e9e9e9_e9e9e9_10)
                        clReading.visibility = View.VISIBLE
                        clRead.visibility = View.GONE
                        readType = "reading"
                        readStartDate = ""
                        readEndDate = ""
                    }
                }

                if(it.read_start_date != "none"){
                    etStart.setText(it.read_start_date.split(" ")[0])
                    etStartDate.setText(it.read_start_date.split(" ")[0])
                }
                if(it.read_end_date != "none"){
                    etEndDate.setText(it.read_end_date.split(" ")[0])
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.VISIBLE
        _binding = null
    }
}