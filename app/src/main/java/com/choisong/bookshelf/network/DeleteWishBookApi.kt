package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.DeleteHaveBookData
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Path

interface DeleteWishBookApi {
    @DELETE("wish-books/{wish_book_idx}")
    suspend fun deleteWishBook(
        @Header("Authorization") accessToken: String,
        @Path("wish_book_idx") bookIdx: Int
    ): Response<DeleteHaveBookData>
}