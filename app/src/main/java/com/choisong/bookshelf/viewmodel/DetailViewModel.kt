package com.choisong.bookshelf.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choisong.bookshelf.model.*
import com.choisong.bookshelf.network.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val bookDetailApi: GetBookDetailApi,
    private val addMyBookApi: PostMyBookApi,
    private val bookMemoApi: GetBookMemoApi): ViewModel(){

    private var _addMyBookResult = MutableLiveData<Boolean>()
    val addMyBookResult: LiveData<Boolean>
    get() = _addMyBookResult

    private var _addWishBookResult = MutableLiveData<String>()
    val addWishBookResult: LiveData<String>
        get() = _addWishBookResult

    private var _bookDetailResult = MutableLiveData<BookDetailDataResult>()
    val bookDetailResult: LiveData<BookDetailDataResult>
    get() = _bookDetailResult

    private var _deleteCommentResult = MutableLiveData<Boolean>()
    val deleteCommentResult: LiveData<Boolean>
    get() = _deleteCommentResult

    private var _bookMemoResult = MutableLiveData<ArrayList<BookMemoDataResult>>()
    val bookMemoResult: LiveData<ArrayList<BookMemoDataResult>>
    get() = _bookMemoResult

    fun addMyBook(accessToken: String, addMyBookModel: AddMyBookModel){
        viewModelScope.launch {
            addMyBookApi.addMyBooks("Bearer $accessToken", addMyBookModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "addMyBooks: Success $result")
                        _addMyBookResult.postValue(result!!.result)
                    }
                }else {
                    Log.d("TAG", "addMyBooks: Failed")
                }
            }
        }
    }

    fun getBookDetail(accessToken: String, bookIsbn: String){
        viewModelScope.launch {
            bookDetailApi.getBookDetail("Bearer $accessToken", bookIsbn).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "getBookDetail: Success $result")
                        _bookDetailResult.postValue(result!!.data)
                    }
                }else {
                    Log.d("TAG", "getBookDetail: Failed")
                }
            }
        }
    }

    fun getBookMemo(accessToken: String, bookIsbn: String, type: String){
        viewModelScope.launch {
            bookMemoApi.getBookMemo("Bearer $accessToken", bookIsbn, type).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "getBookMemo: Success $result")
                        _bookMemoResult.postValue(result!!.data)
                    }
                }else {
                    Log.d("TAG", "getBookMemo: Failed")
                }
            }
        }
    }
}