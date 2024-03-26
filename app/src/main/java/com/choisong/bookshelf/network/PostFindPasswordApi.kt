package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.EmailCheckModel
import com.choisong.bookshelf.model.FindPasswordModel
import com.choisong.bookshelf.model.IdCheckData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PostFindPasswordApi {
    @POST("authentications/find/password")
    suspend fun findPassword(
        @Body findPasswordModel: FindPasswordModel
    ): Response<IdCheckData>
}