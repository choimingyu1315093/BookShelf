package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.AlarmListData
import com.choisong.bookshelf.model.AlarmListDataResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GetAlarmListApi {
    @GET("alarms/list")
    suspend fun alarmList(
        @Header("Authorization") accessToken: String,
    ): Response<AlarmListData>
}