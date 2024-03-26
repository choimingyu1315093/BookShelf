package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.AddWishBookData
import com.choisong.bookshelf.model.DeleteHaveBookData
import com.choisong.bookshelf.model.UserSettingModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH

interface PatchUserSettingApi {
    @PATCH("users/setting")
    suspend fun userSetting(
        @Header("Authorization") accessToken: String,
        @Body userSettingModel: UserSettingModel
    ): Response<DeleteHaveBookData>
}