package com.choisong.bookshelf.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.choisong.bookshelf.model.AddHaveBookModel
import com.choisong.bookshelf.model.BookDetailDataResult
import com.choisong.bookshelf.model.HaveBookDataResult
import com.choisong.bookshelf.model.WishBookDataResult
import com.choisong.bookshelf.network.GetBookDetailApi
import com.choisong.bookshelf.network.GetHaveBookApi
import com.choisong.bookshelf.network.GetWishBookApi
import com.choisong.bookshelf.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class BookProcessViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val haveBookApi: GetHaveBookApi,
    private val wishBookApi: GetWishBookApi,
    private val bookDetailApi: GetBookDetailApi): ViewModel(){

    private var _haveBookResult = MutableLiveData<ArrayList<HaveBookDataResult>?>()
    val haveBookResult: LiveData<ArrayList<HaveBookDataResult>?>
        get() = _haveBookResult

    private var _wishBookResult = MutableLiveData<ArrayList<WishBookDataResult>?>()
    val wishBookResult: LiveData<ArrayList<WishBookDataResult>?>
        get() = _wishBookResult

    private var _bookDetailResult = MutableLiveData<BookDetailDataResult>()
    val bookDetailResult: LiveData<BookDetailDataResult>
        get() = _bookDetailResult

    //보유 중인 책
    fun haveBook(accessToken: String){
        viewModelScope.launch {
            haveBookApi.haveBook("Bearer $accessToken", "y").let { response ->
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

    //읽고 싶은 책
    fun wishBook(accessToken: String, readType: String){
        viewModelScope.launch {
            wishBookApi.wishBook("Bearer $accessToken", readType).let { response ->
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
}