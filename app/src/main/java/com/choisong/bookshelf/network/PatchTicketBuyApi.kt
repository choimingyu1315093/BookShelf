package com.choisong.bookshelf.network

import com.choisong.bookshelf.model.SearchBookMoreModel
import com.choisong.bookshelf.model.TicketBuyModel
import com.choisong.bookshelf.model.UserProfileData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH

interface PatchTicketBuyApi {
    @PATCH("tickets/buy")
    suspend fun buyTicket(
        @Header("Authorization") accessToken: String,
        @Body ticketBuyModel: TicketBuyModel
    ): Response<UserProfileData>
}