package com.choisong.bookshelf.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choisong.bookshelf.model.*
import com.choisong.bookshelf.network.GetBestsellerApi
import com.choisong.bookshelf.network.GetSearchBookApi
import com.choisong.bookshelf.network.GetSearchMoreBookApi
import com.choisong.bookshelf.network.PatchUserLocationApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bestsellerApi: GetBestsellerApi,
    private val searchBookApi: GetSearchBookApi,
    private val searchBookMoreApi: GetSearchMoreBookApi,
    private val userLocationApi: PatchUserLocationApi): ViewModel() {

    private var _itemList = MutableLiveData<List<BestsellerResultData>>()
    val itemList: LiveData<List<BestsellerResultData>>
    get() = _itemList

    private var _popularBook = MutableLiveData<ArrayList<PopularResult>>()
    val popularBook: LiveData<ArrayList<PopularResult>>
    get() = _popularBook

    private var _generalBook = MutableLiveData<ArrayList<GeneralResult>>()
    val generalBook: LiveData<ArrayList<GeneralResult>>
        get() = _generalBook

    private var _generalMoreBook = MutableLiveData<ArrayList<GeneralResult>>()
    val generalMoreBook: LiveData<ArrayList<GeneralResult>>
        get() = _generalMoreBook

    private var _isEnd = MutableLiveData<Boolean>()
    val isEnd: LiveData<Boolean>
    get() = _isEnd

    fun getBestseller(categoryId: Int, searchTarget: String){
        viewModelScope.launch {
            bestsellerApi.bestsellerList(categoryId = categoryId, searchTarget = searchTarget).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "bestsellerList: Success $result")
                        _itemList.postValue(result?.item)
                    }
                }else {
                    Log.d("TAG", "bestsellerList: Failed")
                }
            }
        }
    }

    fun getSearchBookList(accessToken: String, bookName: String){
        viewModelScope.launch {
            searchBookApi.bookList("Bearer $accessToken", bookName).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "bookList: Success $result")
                        _popularBook.postValue(result?.data?.popular_result)
                        _generalBook.postValue(result?.data?.general_result)
                        _isEnd.postValue(result?.data?.is_end)
                    }
                }else {
                    Log.d("TAG", "bookList: Failed")
                }
            }
        }
    }

    fun getSearchBookMoreList(accessToken: String, searchBookMoreModel: SearchBookMoreModel){
        viewModelScope.launch {
            searchBookMoreApi.moreBookList("Bearer $accessToken", searchBookMoreModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "moreBookList: Success $result")
                        _generalMoreBook.postValue(result?.data)
                        _isEnd.postValue(result?.is_end)
                    }
                }else {
                    Log.d("TAG", "moreBookList: Failed")
                }
            }
//            searchBookMoreApi.moreBookList(page, bookName).let { response ->
//                if(response.isSuccessful){
//                    response.body().let { result ->
//                        Log.d("TAG", "bookList: Success $result")
//                        _generalMoreBook.postValue(result?.data)
//                        _isEnd.postValue(result?.is_end)
//                    }
//                }else {
//                    Log.d("TAG", "bookList: Failed")
//                }
//            }
        }
    }

    fun setUserLocation(accessToken: String, userLocationModel: UserLocationModel){
        viewModelScope.launch {
            userLocationApi.setUserLocation("Bearer $accessToken" ,userLocationModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "setUserLocation: Success $result")
                    }
                }else {
                    Log.d("TAG", "setUserLocation: Failed")
                }
            }
        }
    }
}