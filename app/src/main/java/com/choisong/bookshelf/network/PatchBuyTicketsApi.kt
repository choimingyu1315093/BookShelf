package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.BuyTicketData
import com.choisong.bookshelf.model.SearchBookMoreModel
import com.choisong.bookshelf.model.TicketBuyModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH

interface PatchBuyTicketsApi {
    @PATCH("tickets/buy")
    suspend fun buyTickets(
        @Header("Authorization") accessToken: String,
        @Body ticketBuyModel: TicketBuyModel
    ): Response<BuyTicketData>
}