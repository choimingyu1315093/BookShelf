package com.choisong.bookshelf.model

data class TicketLogData(
    val result: Boolean,
    val data: ArrayList<TicketLogDataResult>
)

data class TicketLogDataResult(
    val create_date: String,
    val ticket_log_count: Int,
    val ticket_log_description: String,
    val ticket_log_idx: Int
)