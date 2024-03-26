package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.IdCheckData
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface PostIdCheckApi {
    @POST("authentications/duplicate/id/{user_id}")
    suspend fun idCheck(
        @Path("user_id") userId: String
    ): Response<IdCheckData>
}