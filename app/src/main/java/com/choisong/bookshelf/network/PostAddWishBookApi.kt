package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.AddHaveBookData
import com.choisong.bookshelf.model.AddHaveBookModel
import com.choisong.bookshelf.model.AddWishBookData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PostAddWishBookApi {
    @POST("wish-books")
    suspend fun addWishBook(
        @Header("Authorization") accessToken: String,
        @Body addHaveBookModel: AddHaveBookModel
    ): Response<AddWishBookData>
}