package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.EmailCheckModel
import com.choisong.bookshelf.model.IdCheckData
import com.choisong.bookshelf.model.SignUpModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PostSignUpApi {
    @POST("authentications/sign-up")
    suspend fun signUp(
        @Body signUpModel: SignUpModel
    ): Response<IdCheckData>
}