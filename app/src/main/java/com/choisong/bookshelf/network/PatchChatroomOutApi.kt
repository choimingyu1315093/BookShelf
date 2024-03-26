package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.ChatroomOutData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface PatchChatroomOutApi {
    @PATCH("chat-rooms/{chat_room_idx}")
    suspend fun chatroomOut(
        @Header("Authorization") accessToken: String,
        @Path("chat_room_idx") chatroomIdx: Int
    ): Response<ChatroomOutData>
}