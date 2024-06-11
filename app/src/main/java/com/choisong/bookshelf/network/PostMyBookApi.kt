package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PostMyBookApi {
    @POST("my-books")
    suspend fun addMyBooks(
        @Header("Authorization") accessToken: String,
        @Body addMyBookModel: AddMyBookModel
    ): Response<AddMyBookData>
}