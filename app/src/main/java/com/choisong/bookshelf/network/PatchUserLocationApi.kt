package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.DeleteHaveBookData
import com.choisong.bookshelf.model.UserLocationModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH

interface PatchUserLocationApi {
    @PATCH("users/location")
    suspend fun setUserLocation(
        @Header("Authorization") accessToken: String,
        @Body userLocationModel: UserLocationModel
    ): Response<DeleteHaveBookData>
}