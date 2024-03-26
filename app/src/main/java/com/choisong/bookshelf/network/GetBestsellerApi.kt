package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.BestsellerData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetBestsellerApi {
    @GET("ItemList.aspx")
    suspend fun bestsellerList(
        @Query("ttbkey") ttbkey: String = "ttbthdwhddnd4271541001",
        @Query("QueryType") queryType: String = "Bestseller",
        @Query("MaxResults") maxResults: String = "15",
        @Query("start") start: Int = 1,
        @Query("SearchTarget") searchTarget: String,
        @Query("output") output: String = "js",
        @Query("version") version: String = "20131101",
        @Query("Cover") cover: String = "Big",
        @Query("CategoryId") categoryId: Int
    ): Response<BestsellerData>
}