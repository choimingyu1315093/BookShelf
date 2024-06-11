package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.AlarmCountData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GetAlarmCountApi {
    @GET("alarms/count")
    suspend fun getAlarmCount(
        @Header("Authorization") accessToken: String
    ): Response<AlarmCountData>
}