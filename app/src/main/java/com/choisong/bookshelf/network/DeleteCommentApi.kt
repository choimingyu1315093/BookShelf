package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.DeleteHaveBookData
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Path

interface DeleteCommentApi {
    @DELETE("book-comments/{book_comment_idx}")
    suspend fun deleteComment(
        @Header("Authorization") accessToken: String,
        @Path("book_comment_idx") bookCommentIdx: Int
    ): Response<DeleteHaveBookData>
}