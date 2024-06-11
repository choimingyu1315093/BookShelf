package com.choisong.bookshelf.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.choisong.bookshelf.model.MyProfileResultData
import com.choisong.bookshelf.model.ProfileActiveDataResult
import com.choisong.bookshelf.model.ProfileMemoDataResult
import com.choisong.bookshelf.model.UserSettingModel
import com.choisong.bookshelf.network.*
import com.choisong.bookshelf.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getMyProfileApi: GetMyProfileApi,
    private val patchUserSettingApi: PatchUserSettingApi,
    private val getProfileActiveApi: GetProfileActiveApi,
    private val getProfileMemoApi: GetProfileMemoApi,
    private val patchNicknameApi: PatchNicknameApi,
    private val nicknameCheckApi: PostNicknameCheckApi,
    private val patchDescriptionApi: PatchDescriptionApi): ViewModel(){

    private var _myProfile = MutableLiveData<MyProfileResultData>()
    val myProfile: LiveData<MyProfileResultData>
    get() = _myProfile

    private var _activeList = MutableLiveData<ArrayList<ProfileActiveDataResult>>()
    val activeList: LiveData<ArrayList<ProfileActiveDataResult>>
    get() = _activeList

    private var _memoList = MutableLiveData<ArrayList<ProfileMemoDataResult>>()
    val memoList: LiveData<ArrayList<ProfileMemoDataResult>>
    get() = _memoList

    private var _nicknameResult = MutableLiveData<Boolean>()
    val nicknameResult: LiveData<Boolean>
        get() = _nicknameResult

    private var _nicknameCheckResult = MutableLiveData<Boolean>()
    val nicknameCheckResult: LiveData<Boolean>
        get() = _nicknameCheckResult

    private var _descriptionResult = MutableLiveData<Boolean>()
    val descriptionResult: LiveData<Boolean>
        get() = _descriptionResult

    fun myProfile(accessToken: String){
        viewModelScope.launch {
            getMyProfileApi.myProfile("Bearer $accessToken").let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "myProfile: Success $result")
                        _myProfile.postValue(result!!.data)
                    }
                }else {
                    Log.d("TAG", "myProfile: Failed")
                }
            }
        }
    }

    fun activeList(accessToken: String, userIdx: Int){
        viewModelScope.launch {
            getProfileActiveApi.getActive("Bearer $accessToken", userIdx).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "getActive: Success $result")
                        _activeList.postValue(result!!.data)
                    }
                }else {
                    Log.d("TAG", "getActive: Failed")
                }
            }
        }
    }

    fun memoList(accessToken: String){
        viewModelScope.launch {
            getProfileMemoApi.getMemo("Bearer $accessToken").let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "getMemo: Success $result")
                        _memoList.postValue(result!!.data)
                    }
                }else {
                    Log.d("TAG", "getMemo: Failed")
                }
            }
        }
    }

    fun saveNickname(accessToken: String, nickname: String){
        viewModelScope.launch {
            patchNicknameApi.saveNickname("Bearer $accessToken", nickname).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "saveNickname: Success $result")
                        _nicknameResult.postValue(result!!.result)
                    }
                }else {
                    Log.d("TAG", "saveNickname: Failed")
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

    fun saveDescription(accessToken: String, description: String){
        viewModelScope.launch {
            patchDescriptionApi.saveDescription("Bearer $accessToken", description).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "saveDescription: Success $result")
                        _descriptionResult.postValue(result!!.result)
                    }
                }else {
                    Log.d("TAG", "saveDescription: Failed")
                }
            }
        }
    }
}