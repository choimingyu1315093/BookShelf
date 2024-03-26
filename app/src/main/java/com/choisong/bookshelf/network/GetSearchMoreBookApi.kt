package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.SearchBookData
import com.choisong.bookshelf.model.SearchBookMoreData
import com.choisong.bookshelf.model.SearchBookMoreModel
import retrofit2.Response
import retrofit2.http.*

interface GetSearchMoreBookApi {
    @POST("books/list/more")
    suspend fun moreBookList(
        @Header("Authorization") accessToken: String,
        @Body searchBookMoreModel: SearchBookMoreModel
    ): Response<SearchBookMoreData>
}
