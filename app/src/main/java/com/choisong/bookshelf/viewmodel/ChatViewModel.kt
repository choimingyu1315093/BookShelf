package com.choisong.bookshelf.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.choisong.bookshelf.model.ChatMessageModel
import com.choisong.bookshelf.network.DeleteChatroomApi
import com.choisong.bookshelf.network.GetChatroomDetailApi
import com.choisong.bookshelf.network.PostChatMessageApi
import com.choisong.bookshelf.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val deleteChatroomApi: DeleteChatroomApi): ViewModel(){

    private var _deleteResult = MutableLiveData<Boolean>()
    val deleteResult: LiveData<Boolean>
    get() = _deleteResult

    fun deleteChatroom(accessToken: String, chatroomIdx: Int){
        viewModelScope.launch {
            deleteChatroomApi.deleteChatroom("Bearer $accessToken", chatroomIdx).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "deleteChatroom: Success $result")
                        _deleteResult.postValue(result!!.result)
                    }
                }else {
                    Log.d("TAG", "deleteChatroom: Failed")
                }
            }
        }
    }
}