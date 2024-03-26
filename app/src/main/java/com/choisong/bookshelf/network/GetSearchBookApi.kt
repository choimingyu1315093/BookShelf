package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.SearchBookData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetSearchBookApi {
    @GET("books/list")
    suspend fun bookList(
        @Header("Authorization") accessToken: String,
        @Query("book_name") bookName: String
    ): Response<SearchBookData>
}