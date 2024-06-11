package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.AddCommentData
import com.choisong.bookshelf.model.AddCommentModel
import com.choisong.bookshelf.model.AddMemoData
import com.choisong.bookshelf.model.AddMemoModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PostAddMemoApi {
    @POST("memos")
    suspend fun addMemo(
        @Header("Authorization") accessToken: String,
        @Body addMemoModel: AddMemoModel
    ): Response<AddMemoData>
}