package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.IdCheckData
import com.choisong.bookshelf.model.SignInData
import com.choisong.bookshelf.model.SignInModel
import com.choisong.bookshelf.model.SignUpModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PostSignInApi {
    @POST("authentications/sign-in")
    suspend fun signIn(
        @Body signInModel: SignInModel
    ): Response<SignInData>
}