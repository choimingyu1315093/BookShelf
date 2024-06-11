package com.choisong.bookshelf.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choisong.bookshelf.model.AddCommentModel
import com.choisong.bookshelf.model.AddMemoModel
import com.choisong.bookshelf.network.PostAddMemoApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(private val addMemoApi: PostAddMemoApi): ViewModel(){
    private var _addMemoResult = MutableLiveData<Boolean>()
    val addMemoResult: LiveData<Boolean>
        get() = _addMemoResult

    fun addMemo(accessToken: String, addMemoModel: AddMemoModel){
        viewModelScope.launch {
            addMemoApi.addMemo("Bearer $accessToken", addMemoModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "addMemo: Success $result")
                        _addMemoResult.postValue(result?.result)
                    }
                }else {
                    Log.d("TAG", "addMemo: Failed")
                }
            }
        }
    }
}