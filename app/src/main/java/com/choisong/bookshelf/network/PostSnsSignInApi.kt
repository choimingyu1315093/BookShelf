package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.SignInData
import com.choisong.bookshelf.model.SignInModel
import com.choisong.bookshelf.model.SnsSignInModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PostSnsSignInApi {
    @POST("authentications/sign-in")
    suspend fun signIn(
        @Body snsSignInModel: SnsSignInModel
    ): Response<SignInData>
}