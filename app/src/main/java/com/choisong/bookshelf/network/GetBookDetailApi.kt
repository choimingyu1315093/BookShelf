package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.BookDetailData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetBookDetailApi {
    @GET("books/{book_isbn}")
    suspend fun getBookDetail(
        @Path("book_isbn") bookIsbn: String
    ): Response<BookDetailData>
}