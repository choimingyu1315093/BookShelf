package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.ChatroomDetailData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GetChatroomDetailApi {
    @GET("chat-rooms/{chat_room_idx}")
    suspend fun chatroomDetail(
        @Header("Authorization") accessToken: String,
        @Path("chat_room_idx") chatroomIdx: Int
    ): Response<ChatroomDetailData>
}