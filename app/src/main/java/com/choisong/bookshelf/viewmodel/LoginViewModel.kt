package com.choisong.bookshelf.viewmodel

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.model.*
import com.choisong.bookshelf.network.*
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val findIdApi: PostFindIdApi,
    private val findPasswordApi: PostFindPasswordApi,
    private val changePasswordApi: PatchChangePasswordApi,
    private val signInApi: PostSignInApi,
    private val snsSignInApi: PostSnsSignInApi,
    private val myProfileApi: GetMyProfileApi,
    private val signUpApi: PostSignUpApi,
    private val snsSignUpApi: PostSnsSignUpApi): ViewModel(){

    private var _findIdResult = MutableLiveData<Boolean>()
    val findIdResult: LiveData<Boolean>
        get() = _findIdResult

    private var _findPasswordResult = MutableLiveData<Boolean>()
    val findPasswordResult: LiveData<Boolean>
    get() = _findPasswordResult

    private var _changePasswordResult = MutableLiveData<Boolean>()
    val changePasswordResult: LiveData<Boolean>
    get() = _changePasswordResult

    private var _signInResult = MutableLiveData<Boolean>()
    val signInResult: LiveData<Boolean>
        get() = _signInResult

    private var _snsSignInResult = MutableLiveData<Boolean>()
    val snsSignInResult: LiveData<Boolean>
        get() = _snsSignInResult

    private var _myProfileResult = MutableLiveData<Boolean>()
    val myProfileResult: LiveData<Boolean>
        get() = _myProfileResult

    private var _signUpResult = MutableLiveData<Boolean>()
    val signUpResult: LiveData<Boolean>
        get() = _signUpResult

    private var _snsSignUpResult = MutableLiveData<Boolean>()
    val snsSignUpResult: LiveData<Boolean>
        get() = _snsSignUpResult

    fun findId(findIdModel: FindIdModel){
        viewModelScope.launch {
            findIdApi.findId(findIdModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "findId: Success $result")
                        _findIdResult.postValue(result!!.result)
                        MyApplication.prefs.setId("id", result.data.data)
                    }
                }else {
                    Log.d("TAG", "findId: Failed")
                    _findIdResult.postValue(false)
                }
            }
        }
    }

    fun findPassword(findPasswordModel: FindPasswordModel){
        viewModelScope.launch {
            findPasswordApi.findPassword(findPasswordModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "findPassword: Success $result")
                        _findPasswordResult.postValue(result!!.result)
                    }
                }else {
                    Log.d("TAG", "findPassword: Failed")
                    _findPasswordResult.postValue(false)
                }
            }
        }
    }

    fun changePassword(accessToken: String, changePasswordModel: ChangePasswordModel){
        viewModelScope.launch {
            changePasswordApi.changePassword("Bearer $accessToken", changePasswordModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "`changePassword`: Success $result")
                        _changePasswordResult.postValue(result?.result)
                    }
                }else {
                    response.body().let { result ->
                        Log.d("TAG", "changePassword: Failed $result")
                        _changePasswordResult.postValue(false)
                    }
                }
            }
        }
    }

    fun signIn(signInModel: SignInModel){
        viewModelScope.launch {
            signInApi.signIn(signInModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "signIn: Success $result")
                        MyApplication.prefs.setAutoLogin("autoLogin", true)
                        MyApplication.prefs.setLoginId("loginId", signInModel.user_id!!)
                        MyApplication.prefs.setLoginPw("loginPw", signInModel.user_password!!)
                        MyApplication.prefs.setAccessToken("accessToken", result!!.data.access_token)
                        MyApplication.prefs.setUserIdx("userIdx", result.data.user_idx)
                        _signInResult.postValue(result.result)
                    }
                }else {
                    Log.d("TAG", "signIn: Failed")
                    _signInResult.postValue(false)
                }
            }
        }
    }

    fun snsSignIn(snsSignInModel: SnsSignInModel){
        viewModelScope.launch {
            snsSignInApi.signIn(snsSignInModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "snsSignIn: Success $result")
                        MyApplication.prefs.setAutoLogin("autoLogin", true)
                        MyApplication.prefs.setAccessToken("accessToken", result!!.data.access_token)
                        MyApplication.prefs.setUserIdx("userIdx", result.data.user_idx)
                        _snsSignInResult.postValue(result.result)
                    }
                }else {
                    Log.d("TAG", "snsSignIn: Failed")
                    _snsSignInResult.postValue(false)
                }
            }
        }
    }

    fun myProfile(accessToken: String){
        viewModelScope.launch {
            myProfileApi.myProfile("Bearer $accessToken").let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "myProfile: Success $result")
                        MyApplication.prefs.setNickname("nickname", result!!.data.user_name!!)
                        MyApplication.prefs.setDescription("description", result.data.user_description!!)
                        _myProfileResult.postValue(result.result)
                    }
                }else {
                    Log.d("TAG", "myProfile: Failed")
                    _myProfileResult.postValue(false)
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

    fun snsSignUp(snsSignUpModel: SnsSignUpModel){
        viewModelScope.launch {
            snsSignUpApi.signUp(snsSignUpModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "snsSignUp: Success $result")
                        MyApplication.prefs.setAccessToken("accessToken", result!!.data.access_token)
                        _snsSignUpResult.postValue(result.result)
                    }
                }else {
                    response.body().let { result ->
                        Log.d("TAG", "snsSignUp: Failed $result")
                        _snsSignUpResult.postValue(false)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}