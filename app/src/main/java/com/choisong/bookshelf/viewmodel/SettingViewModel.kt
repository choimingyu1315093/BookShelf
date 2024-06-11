package com.choisong.bookshelf.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choisong.bookshelf.model.ChangePasswordModel
import com.choisong.bookshelf.model.SettingDataResult
import com.choisong.bookshelf.model.UserSettingModel
import com.choisong.bookshelf.network.DeleteUserApi
import com.choisong.bookshelf.network.GetUserSettingApi
import com.choisong.bookshelf.network.PatchChangePasswordApi
import com.choisong.bookshelf.network.PatchUserSettingApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userSettingApi: GetUserSettingApi,
    private val patchUserSettingApi: PatchUserSettingApi,
    private val changePasswordApi: PatchChangePasswordApi,
    private val deleteUserApi: DeleteUserApi): ViewModel() {

    private var _userSettingResult = MutableLiveData<SettingDataResult>()
    val userSettingResult: LiveData<SettingDataResult>
    get() = _userSettingResult

    private var _changePasswordResult = MutableLiveData<Boolean>()
    val changePasswordResult: LiveData<Boolean>
    get() = _changePasswordResult

    private var _deleteResult = MutableLiveData<Boolean>()
    val deleteResult: LiveData<Boolean>
        get() = _deleteResult

    fun getUserSetting(accessToken: String){
        viewModelScope.launch {
            userSettingApi.getUserSetting("Bearer $accessToken").let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "getUserSetting: Success $result")
                        _userSettingResult.postValue(result!!.data)
                    }
                }else {
                    Log.d("TAG", "getUserSetting: Failed")
                }
            }
        }
    }

    fun patchUserSetting(accessToken: String, userSettingModel: UserSettingModel){
        viewModelScope.launch {
            patchUserSettingApi.userSetting("Bearer $accessToken", userSettingModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "userSetting: Success $result")
                    }
                }else {
                    Log.d("TAG", "userSetting: Failed")
                }
            }
        }
    }

    fun changePassword(accessToken: String, changePasswordModel :ChangePasswordModel){
        viewModelScope.launch {
            changePasswordApi.changePassword("Bearer $accessToken", changePasswordModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "changePassword: Success $result")
                        _changePasswordResult.postValue(result!!.result)
                    }
                }else {
                    Log.d("TAG", "changePassword: Failed")
                    _changePasswordResult.postValue(false)
                }
            }
        }
    }

    fun deleteUser(accessToken: String){
        viewModelScope.launch {
            deleteUserApi.userDelete("Bearer $accessToken").let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "userDelete: Success $result")
                        _deleteResult.postValue(result!!.result)
                    }
                }else {
                    Log.d("TAG", "userDelete: Failed")
                }
            }
        }
    }
}