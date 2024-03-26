package com.choisong.bookshelf.view.fragment.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentHomeBinding
import com.choisong.bookshelf.model.*
import com.choisong.bookshelf.view.activity.HomeActivity
import com.choisong.bookshelf.view.adapter.BestsellerAdapter
import com.choisong.bookshelf.view.adapter.SearchBookAdapter
import com.choisong.bookshelf.view.adapter.SearchMoreBookAdapter
import com.choisong.bookshelf.view.dialog.BestsellerSearchDialog
import com.choisong.bookshelf.view.dialog.NearBookDialog
import com.choisong.bookshelf.viewmodel.HomeViewModel
import com.choisong.bookshelf.viewmodel.NotificationViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.net.URL

@AndroidEntryPoint
class HomeFragment : Fragment(), BestsellerAdapter.OnClickListener, BestsellerSearchDialog.OnDialogCancel, SearchBookAdapter.OnClickListener, SearchMoreBookAdapter.OnClickListener{
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private val notificationViewModel: NotificationViewModel by viewModels()

    companion object {
        const val TAG = "HomeFragment"
    }

    lateinit var accessToken: String

    var nick = MyApplication.prefs.getNickname("nickname", "")

    lateinit var bestsellerAdapter: BestsellerAdapter
    lateinit var searchBookAdapter: SearchBookAdapter
    lateinit var searchMoreBookAdapter: SearchMoreBookAdapter
    var pageCount = 1
    var bookTitle = ""
    var generalBookList: ArrayList<GeneralResult> = arrayListOf()
    
    private var country = MyApplication.prefs.getBestsellerCountry("country", "Book")
    private var num = MyApplication.prefs.getBestsellerNum("num", 0)

    private var popularIsbnList = arrayListOf<String>()

    private var popularBookZero = false
    private var generalBookZero = false

    private var isSearch = false
    private var addBook = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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

        val userLocation = UserLocationModel(MyApplication.prefs.getLatitude("lat", 0f), MyApplication.prefs.getLongitude("lng", 0f))
        Log.d(TAG, "init: userLocation $userLocation")
        homeViewModel.setUserLocation(accessToken, userLocation)
        homeViewModel.getBestseller(num, country)
        notificationViewModel.alarmList(accessToken)

        tvNick.text = "Hi, ${nick}님"

        cl.setOnClickListener {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(etSearch.windowToken, 0)
        }
    }

    private fun bindViews() = with(binding){
        ivBell.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_notificationFragment)
        }

        etSearch.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                isSearch = true
                searchKeyword(etSearch.text.toString().trim())
                val manager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(etSearch.windowToken, 0)
//                view?.clearFocus()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

        ivBestsellerSearch.setOnClickListener {
            val dialog = BestsellerSearchDialog(this@HomeFragment)
            dialog.show(requireActivity().supportFragmentManager, "BestsellerSearchDialog")
        }

        btnMore.setOnClickListener {
            if(generalBookList.size == 0){
                addBook = true
                val searchBookMoreModel = SearchBookMoreModel(popularIsbnList, pageCount, bookTitle, "")
                homeViewModel.getSearchBookMoreList(accessToken, searchBookMoreModel)
                pageCount++
            }else {
                addBook = true
                pageCount++
                val searchBookMoreModel = SearchBookMoreModel(popularIsbnList, pageCount, bookTitle, "")
                homeViewModel.getSearchBookMoreList(accessToken, searchBookMoreModel)
            }
        }
    }

    private fun observeViewModel() = with(binding){
        homeViewModel.itemList.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                bestsellerAdapter = BestsellerAdapter(it, this@HomeFragment)
                rvBestseller.apply {
                    layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                    adapter = bestsellerAdapter
                }
            }
            progressBar.visibility = View.GONE
        }

        homeViewModel.popularBook.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                popularIsbnList.clear()
                for(i in 0 until it.size){
                    popularIsbnList.add(it[i].book_isbn)
                }

                txtSearchBook.visibility = View.VISIBLE
                rvSearchBook.visibility = View.VISIBLE
                searchBookAdapter = SearchBookAdapter(it, this@HomeFragment)
                rvSearchBook.apply {
                   layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    adapter = searchBookAdapter
                }
                txtSearchMoreBook.text = "더 많은 검색 결과"
            }else {
                popularBookZero = true
                txtSearchMoreBook.text = "검색 결과"
                txtSearchBook.visibility = View.GONE
                rvSearchBook.visibility = View.GONE
            }
        }

        homeViewModel.generalBook.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                if(isSearch){
                    generalBookList.clear()
                    isSearch = false
                }
                generalBookList = it

                txtSearchMoreBook.visibility = View.VISIBLE
                rvSearchMoreBook.visibility = View.VISIBLE
                searchMoreBookAdapter = SearchMoreBookAdapter(generalBookList, this@HomeFragment)
                rvSearchMoreBook.apply {
                    layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    adapter = searchMoreBookAdapter
                }
            }else {
                generalBookZero = true
                if(popularBookZero && generalBookZero && isSearch){
                    Toast.makeText(requireContext(), "검색 결과가 없습니다(일단 Toast)", Toast.LENGTH_SHORT).show()
                    isSearch = false
                }
                txtSearchMoreBook.visibility = View.GONE
                rvSearchMoreBook.visibility = View.GONE
            }
        }

        homeViewModel.generalMoreBook.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                if(addBook){
                    for(i in 0 until it.size){
                        generalBookList.add(it[i])
                    }
                    addBook = false
                }

                searchMoreBookAdapter = SearchMoreBookAdapter(generalBookList, this@HomeFragment)
                rvSearchMoreBook.adapter = searchMoreBookAdapter
            }
        }


        homeViewModel.isEnd.observe(viewLifecycleOwner){
            if(it){
                btnMore.visibility = View.GONE
            }else {
                btnMore.visibility = View.VISIBLE
            }
        }

        notificationViewModel.alarmListResult.observe(viewLifecycleOwner){
            if(it.data?.size != 0){
                tvNotifyCount.visibility = View.VISIBLE
                tvNotifyCount.text = it.data!!.size.toString()
            }else {
                tvNotifyCount.visibility = View.GONE
            }
        }
    }

    //베스트셀러 검색조건
    override fun next(b: Boolean) {
        country = MyApplication.prefs.getBestsellerCountry("country", "Book")
        num = MyApplication.prefs.getBestsellerNum("num", 0)
        Log.d(TAG, "next: $num, $country")
        homeViewModel.getBestseller(num, country)
    }

    //책 검색
    private fun searchKeyword(bookName: String){
        bookTitle = bookName
        homeViewModel.getSearchBookList(accessToken, bookTitle)
    }

    //베스트셀러 상세보기
    override fun bestsellerClick(bestseller: BestsellerResultData) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(bestseller, null, null, "bestseller", null)
        Navigation.findNavController(binding.root).navigate(action)
        (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
    }

    //Popular 상세보기
    override fun popularBookClick(book: PopularResult) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(null, book, null, "popular", null)
        Navigation.findNavController(binding.root).navigate(action)
        (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
    }

    //General 상세보기
    override fun generalBookClick(book: GeneralResult) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(null, null, book, "general", null)
        Navigation.findNavController(binding.root).navigate(action)
        (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}