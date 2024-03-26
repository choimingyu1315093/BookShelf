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
    private val haveBookApi: GetHaveBookApi,
    private val addHaveBookApi: PostAddHaveBookApi,
    private val deleteHaveBookApi: DeleteHaveBookApi,
    private val addWishBookApi: PostAddWishBookApi,
    private val wishBookApi: GetWishBookApi,
    private val deleteWishBookApi: DeleteWishBookApi,
    private val bookDetailApi: GetBookDetailApi,
    private val deleteCommentApi: DeleteCommentApi): ViewModel(){

    private var _haveBookResult = MutableLiveData<ArrayList<HaveBookDataResult>?>()
    val haveBookResult: LiveData<ArrayList<HaveBookDataResult>?>
    get() = _haveBookResult

    private var _addHaveBookResult = MutableLiveData<String>()
    val addHaveBookResult: LiveData<String>
    get() = _addHaveBookResult

    private var _wishBookResult = MutableLiveData<ArrayList<WishBookDataResult>?>()
    val wishBookResult: LiveData<ArrayList<WishBookDataResult>?>
    get() = _wishBookResult

    private var _addWishBookResult = MutableLiveData<String>()
    val addWishBookResult: LiveData<String>
        get() = _addWishBookResult

    private var _bookDetailResult = MutableLiveData<BookDetailDataResult>()
    val bookDetailResult: LiveData<BookDetailDataResult>
    get() = _bookDetailResult

    private var _deleteCommentResult = MutableLiveData<Boolean>()
    val deleteCommentResult: LiveData<Boolean>
    get() = _deleteCommentResult

    fun haveBook(accessToken: String){
        viewModelScope.launch {
            haveBookApi.haveBook("Bearer $accessToken").let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "haveBook: Success $result")
                        _haveBookResult.postValue(result!!.data)
                    }
                }else {
                    Log.d("TAG", "haveBook: Failed")
                }
            }
        }
    }

    fun addHaveBook(accessToken: String, addHaveBookModel: AddHaveBookModel){
        viewModelScope.launch {
            addHaveBookApi.addHaveBook("Bearer $accessToken", addHaveBookModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "addHaveBook: Success $result")
                        _addHaveBookResult.postValue(result!!.data.have_book_idx)
                    }
                }else {
                    Log.d("TAG", "addHaveBook: Failed")
                }
            }
        }
    }

    fun deleteHaveBook(accessToken: String, bookIdx: Int){
        viewModelScope.launch {
            deleteHaveBookApi.deleteHaveBook("Bearer $accessToken", bookIdx).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "deleteHaveBook: Success $result")
                    }
                }else {
                    Log.d("TAG", "deleteHaveBook: Failed")
                }
            }
        }
    }

    fun addWishBook(accessToken: String, addHaveBookModel: AddHaveBookModel){
        viewModelScope.launch {
            addWishBookApi.addWishBook("Bearer $accessToken", addHaveBookModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "addWishBook: Success $result")
                        _addWishBookResult.postValue(result!!.data.wish_book_idx)
                    }
                }else {
                    Log.d("TAG", "addWishBook: Failed")
                }
            }
        }
    }

    fun wishBook(accessToken: String){
        viewModelScope.launch {
            wishBookApi.wishBook("Bearer $accessToken").let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "wishBook: Success $result")
                        _wishBookResult.postValue(result!!.data)
                    }
                }else {
                    Log.d("TAG", "wishBook: Failed")
                }
            }
        }
    }

    fun deleteWishBook(accessToken: String, bookIdx: Int){
        viewModelScope.launch {
            deleteWishBookApi.deleteWishBook("Bearer $accessToken", bookIdx).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "deleteWishBook: Success $result")
                    }
                }else {
                    Log.d("TAG", "deleteWishBook: Failed")
                }
            }
        }
    }

    fun getBookDetail(bookIsbn: String){
        viewModelScope.launch {
            bookDetailApi.getBookDetail(bookIsbn).let { response ->
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

    fun deleteComment(accessToken: String, idx: Int){
        viewModelScope.launch {
            deleteCommentApi.deleteComment("Bearer $accessToken", idx).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "deleteComment: Success $result")
                        _deleteCommentResult.postValue(result!!.result)
                    }
                }else {
                    Log.d("TAG", "deleteComment: Failed")
                }
            }
        }
    }
}