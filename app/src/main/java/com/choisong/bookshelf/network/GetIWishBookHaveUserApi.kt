package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.IWishBookHaveUserData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GetIWishBookHaveUserApi {
    @GET("users/list/wish-book")
    suspend fun getIWishBookHaveUser(
        @Header("Authorization") accessToken: String,
    ): Response<IWishBookHaveUserData>
}