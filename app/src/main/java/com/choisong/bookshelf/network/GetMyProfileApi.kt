package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.MyProfileData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GetMyProfileApi {
    @GET("users/profile")
    suspend fun myProfile(
        @Header("Authorization") accessToken: String
    ): Response<MyProfileData>
}