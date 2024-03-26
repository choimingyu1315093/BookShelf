package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.DeleteHaveBookData
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Header

interface DeleteUserApi {
    @DELETE("authentications")
    suspend fun userDelete(
        @Header("Authorization") accessToken: String
    ): Response<DeleteHaveBookData>
}