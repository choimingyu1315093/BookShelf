package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.DeleteHaveBookData
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Path

interface DeleteChatroomApi {
    @DELETE("chat-rooms/{chat_room_idx}")
    suspend fun deleteChatroom(
        @Header("Authorization") accessToken: String,
        @Path("chat_room_idx") chatroomIdx: Int
    ): Response<DeleteHaveBookData>
}