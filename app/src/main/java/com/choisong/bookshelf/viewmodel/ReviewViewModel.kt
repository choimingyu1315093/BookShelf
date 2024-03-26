package com.choisong.bookshelf.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choisong.bookshelf.model.AddCommentModel
import com.choisong.bookshelf.model.UpdateCommentModel
import com.choisong.bookshelf.network.PostAddCommentApi
import com.choisong.bookshelf.network.PutUpdateCommentApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val addCommentApi: PostAddCommentApi,
    private val updateCommentApi: PutUpdateCommentApi): ViewModel(){

    private var _addCommentResult = MutableLiveData<Boolean>()
    val addCommentResult: LiveData<Boolean>
    get() = _addCommentResult

    private var _updateCommentResult = MutableLiveData<Boolean>()
    val updateCommentResult: LiveData<Boolean>
    get() = _updateCommentResult

    fun addComment(accessToken: String, addCommentModel: AddCommentModel){
        viewModelScope.launch {
            addCommentApi.addComment("Bearer $accessToken", addCommentModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "addComment: Success $result")
                        _addCommentResult.postValue(result?.result)
                    }
                }else {
                    Log.d("TAG", "addComment: Failed")
                }
            }
        }
    }

    fun updateComment(accessToken: String, idx: Int, updateCommentModel: UpdateCommentModel){
        viewModelScope.launch {
            updateCommentApi.updateComment("Bearer $accessToken", idx, updateCommentModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "updateComment: Success $result")
                        _updateCommentResult.postValue(result!!.result)
                    }
                }else {
                    Log.d("TAG", "updateComment: Failed")
                }
            }
        }
    }
}