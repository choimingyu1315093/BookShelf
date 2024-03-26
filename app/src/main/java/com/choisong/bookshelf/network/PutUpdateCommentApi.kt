package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.DeleteHaveBookData
import com.choisong.bookshelf.model.UpdateCommentModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface PutUpdateCommentApi {
    @PUT("book-comments/{book_comment_idx}")
    suspend fun updateComment(
        @Header("Authorization") accessToken: String,
        @Path("book_comment_idx") bookCommentIdx: Int,
        @Body updateCommentModel: UpdateCommentModel
    ): Response<DeleteHaveBookData>
}