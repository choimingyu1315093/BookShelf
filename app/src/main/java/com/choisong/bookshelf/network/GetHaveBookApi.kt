package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.HaveBookData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetHaveBookApi {
    @GET("my-books/list")
    suspend fun haveBook(
        @Header("Authorization") accessToken: String,
        @Query("is_have_book") isHaveBook: String
    ): Response<HaveBookData>
}