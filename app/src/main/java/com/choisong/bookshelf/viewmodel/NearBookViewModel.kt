package com.choisong.bookshelf.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choisong.bookshelf.model.CreateChatroomModel
import com.choisong.bookshelf.network.PostCreateChatroomApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearBookViewModel @Inject constructor(private val postCreateChatroomApi: PostCreateChatroomApi): ViewModel(){

    private var _createChatroomMessage = MutableLiveData<String?>()
    val createChatroomMessage: LiveData<String?>
    get() = _createChatroomMessage

    private var _chatroomIdx = MutableLiveData<Int>()
    val chatroomIdx: LiveData<Int>
    get() = _chatroomIdx

    fun createChatroom(accessToken: String, createChatroomModel: CreateChatroomModel){
        viewModelScope.launch {
            postCreateChatroomApi.createChatroom("Bearer $accessToken", createChatroomModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "createChatroom: Success $result")
                        if(result!!.data.description == null){
                            _chatroomIdx.postValue(result.data.chat_room_idx)
                        }else {
                            _createChatroomMessage.postValue(result.data.description)
                        }
                    }
                }else {
                    Log.d("TAG", "createChatroom: Failed")
                }
            }
        }
    }
}