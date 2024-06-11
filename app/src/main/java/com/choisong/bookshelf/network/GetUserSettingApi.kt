package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.SettingData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GetUserSettingApi {
    @GET("users/setting")
    suspend fun getUserSetting(
        @Header("Authorization") accessToken: String
    ): Response<SettingData>
}