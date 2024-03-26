package com.choisong.bookshelf.network

import androidx.lifecycle.ViewModel
import com.choisong.bookshelf.model.EmailCheckModel
import com.choisong.bookshelf.model.IdCheckData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface PostEmailCheckApi {
    @POST("authentications/duplicate/email")
    suspend fun emailCheck(
        @Body emailCheckModel: EmailCheckModel
    ): Response<IdCheckData>
}