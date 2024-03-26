package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.AddHaveBookData
import com.choisong.bookshelf.model.AddHaveBookModel
import com.choisong.bookshelf.model.ChangePasswordModel
import com.choisong.bookshelf.model.IdCheckData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface PostAddHaveBookApi {
    @POST("have-books")
    suspend fun addHaveBook(
        @Header("Authorization") accessToken: String,
        @Body addHaveBookModel: AddHaveBookModel
    ): Response<AddHaveBookData>
}