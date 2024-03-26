package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.UserProfileData
import com.choisong.bookshelf.model.UserProfileDataResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GetUserProfileApi {
    @GET("users/profile")
    suspend fun getUserProfile(
        @Header("Authorization") accessToken: String,
    ): Response<UserProfileData>
}