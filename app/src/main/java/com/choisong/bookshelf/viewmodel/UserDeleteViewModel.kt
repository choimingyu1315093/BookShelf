package com.choisong.bookshelf.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choisong.bookshelf.network.DeleteUserApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDeleteViewModel @Inject constructor(private val userDeleteApi: DeleteUserApi): ViewModel(){
    private var _userDeleteResult = MutableLiveData<Boolean>()
    val userDeleteResult: LiveData<Boolean>
    get() = _userDeleteResult

    fun userDelete(accessToken: String){
        viewModelScope.launch {
            userDeleteApi.userDelete("Bearer $accessToken").let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "userDelete: Success $result")
                        _userDeleteResult.postValue(result!!.result)
                    }
                }else {
                    Log.d("TAG", "userDelete: Failed")
                }
            }
        }
    }
}