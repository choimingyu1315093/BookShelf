package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.DeleteHaveBookData
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Path

interface DeleteAllAlarmApi {
    @DELETE("alarms/all")
    suspend fun deleteAllAlarm(
        @Header("Authorization") accessToken: String
    ): Response<DeleteHaveBookData>
}