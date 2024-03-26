package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.IdCheckData
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface PostNicknameCheckApi {
    @POST("authentications/duplicate/name/{user_name}")
    suspend fun nicknameCheck(
        @Path("user_name") userName: String
    ): Response<IdCheckData>
}