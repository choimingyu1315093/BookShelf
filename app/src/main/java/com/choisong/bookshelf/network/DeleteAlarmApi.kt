package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.DeleteHaveBookData
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Path

interface DeleteAlarmApi {
    @DELETE("alarms/{alarm_idx}")
    suspend fun deleteAlarm(
        @Header("Authorization") accessToken: String,
        @Path("alarm_idx") alarmIdx: Int
    ): Response<DeleteHaveBookData>
}