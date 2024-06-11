package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.DeleteHaveBookData
import com.choisong.bookshelf.model.ResultData
import com.choisong.bookshelf.model.UserLocationModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface PatchNicknameApi {
    @PATCH("users/name/{user_name}")
    suspend fun saveNickname(
        @Header("Authorization") accessToken: String,
        @Path("user_name") userName: String
    ): Response<ResultData>
}