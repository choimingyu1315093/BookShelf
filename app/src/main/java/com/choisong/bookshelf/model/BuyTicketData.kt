package com.choisong.bookshelf.model

data class BuyTicketData(
    val result: Boolean,
    val data: BuyTicketDataResult
)

data class BuyTicketDataResult(
    val ticket_count: Int
)