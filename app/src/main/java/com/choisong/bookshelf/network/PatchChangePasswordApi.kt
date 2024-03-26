package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.ChangePasswordModel
import com.choisong.bookshelf.model.FindPasswordModel
import com.choisong.bookshelf.model.IdCheckData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface PatchChangePasswordApi {
    @PATCH("authentications/change/password")
    suspend fun changePassword(
        @Header("Authorization") accessToken: String,
        @Body changePasswordModel: ChangePasswordModel
    ): Response<IdCheckData>
}