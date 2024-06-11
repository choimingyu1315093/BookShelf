package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.TicketLogData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GetTicketLogApi {
    @GET("ticket-logs")
    suspend fun ticketLogList(
        @Header("Authorization") accessToken: String
    ): Response<TicketLogData>
}