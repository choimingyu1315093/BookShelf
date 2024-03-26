package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.HaveBookData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GetHaveBookApi {
    @GET("have-books/list")
    suspend fun haveBook(
        @Header("Authorization") accessToken: String
    ): Response<HaveBookData>
}