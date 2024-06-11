package com.choisong.bookshelf.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choisong.bookshelf.model.EmailCheckModel
import com.choisong.bookshelf.model.IdCheckData
import com.choisong.bookshelf.model.SignUpModel
import com.choisong.bookshelf.model.TicketBuyModel
import com.choisong.bookshelf.network.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val idCheckApi: PostIdCheckApi,
    private val nicknameCheckApi: PostNicknameCheckApi,
    private val emailCheckApi: PostEmailCheckApi,
    private val signUpApi: PostSignUpApi): ViewModel(){

    private var _idCheckResult = MutableLiveData<Boolean>()
    val idCheckResult: LiveData<Boolean>
    get() = _idCheckResult

    private var _nicknameCheckResult = MutableLiveData<Boolean>()
    val nicknameCheckResult: LiveData<Boolean>
        get() = _nicknameCheckResult

    private var _emailCheckResult = MutableLiveData<Boolean>()
    val emailCheckResult: LiveData<Boolean>
        get() = _emailCheckResult

    private var _signUpResult = MutableLiveData<Boolean>()
    val signUpResult: LiveData<Boolean>
        get() = _signUpResult

    fun idCheck(userId: String){
        viewModelScope.launch {
            idCheckApi.idCheck(userId).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "idCheck: Success $result")
                        _idCheckResult.postValue(result!!.result)
                    }
                }else {
                    Log.d("TAG", "idCheck: Failed")
                    _idCheckResult.postValue(false)
                }
            }
        }
    }

    fun nicknameCheck(userName: String){
        viewModelScope.launch {
            nicknameCheckApi.nicknameCheck(userName).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "nicknameCheck: Success $result")
                        _nicknameCheckResult.postValue(result!!.result)
                    }
                }else {
                    Log.d("TAG", "nicknameCheck: Failed")
                    _nicknameCheckResult.postValue(false)
                }
            }
        }
    }

    fun emailCheck(emailCheckModel: EmailCheckModel){
        viewModelScope.launch {
            emailCheckApi.emailCheck(emailCheckModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "emailCheck: Success $result")
                        _emailCheckResult.postValue(result!!.result)
                    }
                }else {
                    Log.d("TAG", "emailCheck: Failed")
                    _emailCheckResult.postValue(false)
                }
            }
        }
    }

    fun signUp(signUpModel: SignUpModel){
        viewModelScope.launch {
            signUpApi.signUp(signUpModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "signUp: Success $result")
                        _signUpResult.postValue(result!!.result)
                    }
                }else {
                    Log.d("TAG", "signUp: Failed")
                    _signUpResult.postValue(false)
                }
            }
        }
    }
}