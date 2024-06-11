package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.ProfileActiveData
import com.choisong.bookshelf.model.ProfileMemoData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetProfileMemoApi {
    @GET("memos/list")
    suspend fun getMemo(
        @Header("Authorization") accessToken: String,
    ): Response<ProfileMemoData>
}