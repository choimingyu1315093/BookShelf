package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.FindIdData
import com.choisong.bookshelf.model.FindIdModel
import com.choisong.bookshelf.model.FindPasswordModel
import com.choisong.bookshelf.model.IdCheckData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PostFindIdApi {
    @POST("authentications/find/id")
    suspend fun findId(
        @Body findIdModel: FindIdModel
    ): Response<FindIdData>
}