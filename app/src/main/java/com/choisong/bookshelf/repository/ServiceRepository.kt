package com.choisong.bookshelf.repository

import android.util.Log
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.model.*
import com.choisong.bookshelf.network.*
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class ServiceRepository @Inject constructor(
    private val alarmListApi: GetAlarmListApi,
    private val iWishBookHaveUserApi: GetIWishBookHaveUserApi) {

    val accessToken = MyApplication.prefs.getAccessToken("accessToken", "")


    fun getIWishBookHaveUserList() = flow<IWishBookHaveUserData> {
        val data = iWishBookHaveUserApi.getIWishBookHaveUser("Bearer $accessToken").body()
        emit(data!!)
    }
}