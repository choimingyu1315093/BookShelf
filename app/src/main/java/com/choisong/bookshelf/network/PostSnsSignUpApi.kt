package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.IdCheckData
import com.choisong.bookshelf.model.SignUpModel
import com.choisong.bookshelf.model.SnsSignUpData
import com.choisong.bookshelf.model.SnsSignUpModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PostSnsSignUpApi {
    @POST("authentications/sign-up")
    suspend fun signUp(
        @Body snsSignUpModel: SnsSignUpModel
    ): Response<SnsSignUpData>
}