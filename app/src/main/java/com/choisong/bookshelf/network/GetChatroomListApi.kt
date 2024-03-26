package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.ChatroomListData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GetChatroomListApi {
    @GET("chat-rooms/list")
    suspend fun getChatroomList(
        @Header("Authorization") accessToken: String
    ): Response<ChatroomListData>
}