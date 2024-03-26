package com.choisong.bookshelf.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.choisong.bookshelf.model.UserSettingModel
import com.choisong.bookshelf.network.GetUserProfileApi
import com.choisong.bookshelf.network.PatchUserSettingApi
import com.choisong.bookshelf.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val serviceRepository: ServiceRepository, private val patchUserSettingApi: PatchUserSettingApi): ViewModel(){

    val userProfileResult = serviceRepository.getUserProfile().asLiveData()

    fun userSetting(accessToken: String, userSettingModel: UserSettingModel){
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
}