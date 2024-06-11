package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.BookMemoData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetBookMemoApi {
    @GET("memos/{book_isbn}")
    suspend fun getBookMemo(
        @Header("Authorization") accessToken: String,
        @Query("book_isbn") bookIsbn: String,
        @Query("get_type") getType: String
    ): Response<BookMemoData>
}