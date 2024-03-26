package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.DeleteHaveBookData
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Path

interface DeleteHaveBookApi {
    @DELETE("have-books/{have_book_idx}")
    suspend fun deleteHaveBook(
        @Header("Authorization") accessToken: String,
        @Path("have_book_idx") bookIdx: Int
    ): Response<DeleteHaveBookData>
}

