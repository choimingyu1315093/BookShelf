package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.AddCommentData
import com.choisong.bookshelf.model.AddCommentModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PostAddCommentApi {
    @POST("book-comments")
    suspend fun addComment(
        @Header("Authorization") accessToken: String,
        @Body addCommentModel: AddCommentModel
    ): Response<AddCommentData>
}


