package com.choisong.bookshelf.network

import android.security.identity.ResultData
import com.choisong.bookshelf.model.DeleteHaveBookData
import com.choisong.bookshelf.model.UserLocationModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface PatchDescriptionApi {
    @PATCH("users/description/{user_description}")
    suspend fun saveDescription(
        @Header("Authorization") accessToken: String,
        @Path("user_description") userDescription: String
    ): Response<com.choisong.bookshelf.model.ResultData>
}