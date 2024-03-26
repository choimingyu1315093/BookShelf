package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.CreateChatroomData
import com.choisong.bookshelf.model.CreateChatroomModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PostCreateChatroomApi {
    @POST("chat-rooms")
    suspend fun createChatroom(
        @Header("Authorization") accessToken: String,
        @Body createChatroomModel: CreateChatroomModel
    ): Response<CreateChatroomData>
}