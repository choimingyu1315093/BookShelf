package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.ProfileActiveData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetProfileActiveApi {
    @GET("activities/list")
    suspend fun getActive(
        @Header("Authorization") accessToken: String,
        @Query("user_idx") userIdx: Int
    ): Response<ProfileActiveData>
}