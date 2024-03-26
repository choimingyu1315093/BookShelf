package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.HaveBookData
import com.choisong.bookshelf.model.WishBookData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GetWishBookApi {
    @GET("wish-books/list")
    suspend fun wishBook(
        @Header("Authorization") accessToken: String
    ): Response<WishBookData>
}